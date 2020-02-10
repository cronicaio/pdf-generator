package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.aws.AWSS3BucketAdapter;
import io.cronica.api.pdfgenerator.component.aws.Repeater;
import io.cronica.api.pdfgenerator.component.entity.Issuer;
import io.cronica.api.pdfgenerator.component.wrapper.TemplateContract;
import io.cronica.api.pdfgenerator.database.model.DocumentCertificate;
import io.cronica.api.pdfgenerator.utils.DocumentUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static io.cronica.api.pdfgenerator.utils.Constants.*;

@Slf4j
public class JasperSoftTemplateHandler implements TemplateHandler {

    private final String bankCode;

    private final String dataJson;

    private final String documentID;

    private final String templateID;

    private final Repeater repeater;

    private final AWSS3BucketAdapter awss3BucketAdapter;

    private final TemplateTransactionService transactionService;

    private final IssuerRegistryTransactionService issuerService;

    private File template;

    private TemplateContract templateContract;

    public JasperSoftTemplateHandler(
            Repeater repeater,
            AWSS3BucketAdapter awss3BucketAdapter,
            DocumentCertificate docCert,
            IssuerRegistryTransactionService issuerTransactionService,
            TemplateTransactionService templateTransactionService,
            DocumentTransactionService documentTransactionService
    ) {
        this.repeater = repeater;
        this.awss3BucketAdapter = awss3BucketAdapter;
        this.issuerService = issuerTransactionService;
        this.transactionService = templateTransactionService;

        final String documentAddress = DocumentUtils.readDocumentAddress(docCert.getDocumentID());
        this.documentID = docCert.getDocumentID();
        this.templateID = documentTransactionService.getTemplateID(documentAddress);
        this.bankCode = documentTransactionService.getBankCode(documentAddress);
        this.dataJson = documentTransactionService.getStructuredData(documentAddress);
    }

    /**
     * @see TemplateHandler#generateTemplate()
     */
    @Override
    public void generateTemplate() throws IOException {
        this.templateContract = this.transactionService.loadTemplate(this.templateID);
        if ( !templateIsCached() ) {
            log.info("[SERVICE] generating JasperSoft template with '{}' name", this.template.getName());
            downloadTemplate();
            log.info("[SERVICE] template with name '{}' is created", this.template.getName());
        }
    }
    private boolean templateIsCached() {
        final String fileName = "./temp/template/JrxmlTemplate-" + this.templateID + ".jrxml";
        this.template = new File(fileName);
        return this.template.exists();
    }

    private void downloadTemplate() throws IOException {
        FileUtils.touch(this.template);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.template))) {
            final String content = this.transactionService.getMainContentOfTemplate(this.templateContract);
            writer.write(String.valueOf(content));
        }
    }

    /**
     * @see TemplateHandler#downloadAdditionalFiles()
     */
    @Override
    public void downloadAdditionalFiles() {
        final String imagesFolder = "template/" + this.templateID + "/img/";
        final String fontsFolder = "template/" + this.templateID + "/fonts/";

        String s3Prefix = "template/" + this.templateID + "/img/";
        this.repeater.apply(this.awss3BucketAdapter::downloadFilesWithPrefix, s3Prefix, imagesFolder);

        s3Prefix = "template/" + this.templateID + "/fonts/";
        this.repeater.apply(this.awss3BucketAdapter::downloadFilesWithPrefix, s3Prefix, fontsFolder);
    }

    /**
     * @see TemplateHandler#generatePDFDocument()
     */
    @Override
    public byte[] generatePDFDocument() throws Exception {
        final String fileName = "DC-" + this.documentID + ".pdf";
        final File document = new File(PATH_TO_PDF_DOCUMENTS + "/" + fileName);
        try {
            log.info("[SERVICE] begin generating PDF document using JasperSoft template");
            final ByteArrayOutputStream pdfReportStream = parseTemplate(this.dataJson);
            final OutputStream outPdf = new FileOutputStream(document);

            pdfReportStream.writeTo(outPdf);
            pdfReportStream.close();
            log.info("[SERVICE] PDF document with '{}' ID has generated using JasperSoft template", this.documentID);
            return IOUtils.toByteArray(new FileInputStream(document));
        }
        finally {
            FileUtils.forceDelete(document);
        }
    }

    private ByteArrayOutputStream parseTemplate(String jsonData) throws Exception {
        final Issuer issuer = this.issuerService.getIssuerByBankCode(this.bankCode);
        jsonData = removeCollectionsFromJson(jsonData);

        final String linkToPdfDocument =
                issuer.getFrontEndLink()
                + SEARCH_BY_ID_STRUCTURED_FRONTEND_BASIC_URL
                + this.documentID;
        final File qrCodeImage = generateQrCodeFrom(linkToPdfDocument);

        final Map<String, Object> params = new HashMap<>();
        params.put("PATH_TO_IMAGES", "./template/" + this.templateID + "/img/");
        params.put("QR_CODE_PATH", qrCodeImage.getAbsolutePath());
        params.put("DOCUMENT_LINK", linkToPdfDocument);

        try {
            final JasperDesign design = JRXmlLoader.load(this.template);
            final JasperReport jasperReport = JasperCompileManager.compileReport(design);
            log.info("[SERVICE] JasperSoft report compiled");

            final JsonDataSource jsonDataSource = new JsonDataSource(new ByteArrayInputStream(jsonData.getBytes()));

            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jsonDataSource);

            final JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();

            FileUtils.forceDelete(qrCodeImage);

            return pdfReportStream;
        }
        catch (JRRuntimeException ex) {
            log.error("[SERVICE] font is not found", ex);
            throw new RuntimeException("Font is not found", ex);
        }
        catch (JRException ex) {
            log.error("[SERVICE] template is corrupted or image is not found", ex);
            throw new RuntimeException("Template can't be read or image is not found");
        }
    }

    private File generateQrCodeFrom(String linkToPdfDocument) {
        final File qrCodeImage = new File(
                PATH_TO_FOLDER_WITH_QR_CODE
                + "QR-" + this.documentID + PNG_FILE_EXTENSION
        );
        DocumentUtils.generateQRCodeImage(linkToPdfDocument, QR_CODE_IMAGE_WIDTH, QR_CODE_IMAGE_HEIGHT, qrCodeImage.getAbsolutePath());
        return qrCodeImage;
    }

    private static String removeCollectionsFromJson(final String dataJson) throws IOException {
        final Map<String, Object> initialMap = DocumentUtils.convertJsonStringToMap(dataJson);
        final Map<String, Object> modifiedMap = new HashMap<>();
        for (String key : initialMap.keySet()) {
            if (initialMap.get(key) instanceof Collection<?>) {
                continue;
            }
            modifiedMap.put(key, initialMap.get(key));
        }
        return DocumentUtils.convertMapToJsonString(modifiedMap);
    }
}
