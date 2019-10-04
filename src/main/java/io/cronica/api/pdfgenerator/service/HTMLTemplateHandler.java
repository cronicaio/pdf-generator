package io.cronica.api.pdfgenerator.service;

import com.amazonaws.util.IOUtils;
import io.cronica.api.pdfgenerator.component.aws.AWSS3BucketAdapter;
import io.cronica.api.pdfgenerator.component.aws.Repeater;
import io.cronica.api.pdfgenerator.component.entity.Document;
import io.cronica.api.pdfgenerator.component.entity.Font;
import io.cronica.api.pdfgenerator.component.entity.Issuer;
import io.cronica.api.pdfgenerator.component.wrapper.TemplateContract;
import io.cronica.api.pdfgenerator.database.model.DocumentCertificate;
import io.cronica.api.pdfgenerator.exception.IssuerNotFoundException;
import io.cronica.api.pdfgenerator.utils.DocumentUtils;
import io.cronica.api.pdfgenerator.utils.HTMLUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.cronica.api.pdfgenerator.utils.Constants.*;

@Slf4j
@Scope("prototype")
public class HTMLTemplateHandler implements TemplateHandler {

    private final String bankCode;

    private final String dataJson;

    private final String documentID;

    private final String templateID;

    private final Repeater repeater;

    private final AWSS3BucketAdapter awss3BucketAdapter;

    private final TemplateTransactionService transactionService;

    private final IssuerRegistryTransactionService issuerService;

    private File template;

    private File footerTemplate;

    private File headerTemplate;

    private TemplateContract templateContract;

    public HTMLTemplateHandler(
            Repeater repeater,
            AWSS3BucketAdapter awss3BucketAdapter,
            DocumentCertificate docCert,
            IssuerRegistryTransactionService issuerService,
            TemplateTransactionService templateTransactionService,
            DocumentTransactionService documentTransactionService
    ) {
        this.repeater = repeater;
        this.awss3BucketAdapter = awss3BucketAdapter;
        this.issuerService = issuerService;
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
        log.info("[SERVICE] downloading files of template with {} address",
                 this.templateContract.getContractAddress());
        if ( !templateIsCached() ) {
            downloadMainContent();
        }
        if ( !headerTemplateIsCached() ) {
            downloadHeaderContent();
        }
        if ( !footerTemplateIsCached() ) {
            downloadFooterContent();
        }
        log.info("[SERVICE] all files of template with {} address downloaded",
                 this.templateContract.getContractAddress());
    }

    private boolean templateIsCached() {
        final String fileName = "./temp/template/HtmlTemplate-" + this.templateID + ".html";
        this.template = new File(fileName);
        return this.template.exists();
    }

    private boolean headerTemplateIsCached() {
        final String fileName =
                FilenameUtils.getFullPath(this.template.getAbsolutePath())
                + HEADER_FILE_PREFIX + this.template.getName();
        this.headerTemplate = new File(fileName);

        return this.headerTemplate.exists();
    }

    private boolean footerTemplateIsCached() {
        final String fileName =
                FilenameUtils.getFullPath(this.template.getAbsolutePath())
                + FOOTER_FILE_PREFIX + this.template.getName();
        this.footerTemplate = new File(fileName);

        return this.footerTemplate.exists();
    }

    private void downloadMainContent() throws IOException {
        FileUtils.touch(this.template);
        final String content = this.transactionService.getMainContentOfTemplate(this.templateContract);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.template))) {
            writer.write(String.valueOf(content));
        }
    }

    private void downloadHeaderContent() throws IOException {
        final String content = this.transactionService.getHeaderContentOfTemplate(this.templateContract);
        if ( !StringUtils.isEmpty(content) ) {
            FileUtils.touch(this.headerTemplate);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.headerTemplate))) {
                writer.write(content);
            }
        }
    }

    private void downloadFooterContent() throws IOException {
        final String content = this.transactionService.getFooterContentOfTemplate(this.templateContract);
        if ( !StringUtils.isEmpty(content) ) {
            FileUtils.touch(this.footerTemplate);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.footerTemplate))) {
                writer.write(content);
            }
        }
    }

    /**
     * @see TemplateHandler#downloadAdditionalFiles()
     */
    @Override
    public void downloadAdditionalFiles() {
        final String imagesFolder = "template/" + this.templateID + "/";
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
    public InputStream generatePDFDocument() throws Exception {
        final String uuid = UUID.randomUUID().toString();
        final String fileName = "DC-" + uuid + ".pdf";
        final File document = new File(PATH_TO_PDF_DOCUMENTS + "/" + fileName);

        try {
            log.info("[SERVICE] begin generating a PDF document using HTML template");
            final File preparedTemplate = prepareTemplateForGenerating(uuid);
            importFontsInto(preparedTemplate);
            insertQrCodeIfNeeded();
            final File headerFile = findHeaderFile(preparedTemplate.getAbsolutePath());
            final File footerFile = findFooterFile(preparedTemplate.getAbsolutePath());

            generatePDFWithWkhtmltopdf(
                    preparedTemplate.getAbsolutePath(), document, headerFile, footerFile
            );

            deleteFile(preparedTemplate);
            if (headerFile != null && headerFile.exists()) {
                deleteFile(headerFile);
            }
            if (footerFile != null && footerFile.exists()) {
                deleteFile(footerFile);
            }

            log.info("[SERVICE] successfully generated PDF document with '{}' ID, using HTML", this.documentID);
            return new FileInputStream(document);
        }
        finally {
            deleteFile(document);
        }
    }

    private void deleteFile(final File file) {
        try {
            FileUtils.forceDelete(file);
        }
        catch (FileNotFoundException ex) {
            log.debug("[SERVICE] '{}' file already removed", file.getAbsolutePath());
        }
        catch (IOException ex) {
            log.error("[SERVICE] exception while removing following file: '{}'", file.getAbsolutePath(), ex);
        }
    }

    private File prepareTemplateForGenerating(final String uuid) throws IOException {
        final Map<String, Object> parameters = DocumentUtils.convertJsonStringToMap(this.dataJson);
        final String newTemplateContent = HTMLUtils.modifyTemplate(parameters, this.template);

        final File document = new File("./template/" + this.templateID + "/" + uuid + ".html");
        FileUtils.touch(document);

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(document))) {
            writer.write(String.valueOf(newTemplateContent));
        }

        return document;
    }

    private void importFontsInto(final File templateWithData) throws IOException {
        final List<Font> fontEntityList = HTMLUtils.readFonts("template/" + this.templateID + "/fonts/");
        HTMLUtils.importFontsIntoHtml(templateWithData, fontEntityList);
    }

    private void insertQrCodeIfNeeded() throws Exception {
        final boolean qrCodeImageTagsFound = HTMLUtils.findQRCodeImageTags(this.template);
        if (qrCodeImageTagsFound) {
            final String linkToPdfDocument = getLinkToPdfDocument();
            final File qrCodeImage = generateQrCodeImageFrom(linkToPdfDocument);

            HTMLUtils.insertQrCodeImagePathAndAltText(
                    qrCodeImage.getAbsolutePath(), linkToPdfDocument, this.template
            );
        }
    }

    private String getLinkToPdfDocument() throws IssuerNotFoundException {
        final Issuer issuer = this.issuerService.getIssuerByBankCode(this.bankCode);

        return issuer.getFrontEndLink()
                + SEARCH_BY_ID_STRUCTURED_FRONTEND_BASIC_URL
                + this.documentID;
    }

    private File generateQrCodeImageFrom(final String linkToPdfDocument) {
        final File qrCodeImage = new File(
                PATH_TO_FOLDER_WITH_QR_CODE
                 + "QR-" + this.documentID + PNG_FILE_EXTENSION);
        DocumentUtils.generateQRCodeImage(
                linkToPdfDocument,
                100,
                100,
                qrCodeImage.getAbsolutePath());
        return qrCodeImage;
    }

    private File findHeaderFile(final String pathToTemplate) throws IOException {
        log.info("[SERVICE] generating header");

        if (this.headerTemplate == null || !this.headerTemplate.exists()) {
            log.info("[SERVICE] header template file does not found, skip it");
            return null;
        }

        final File headerHtmlFile = new File(pathToTemplate + "header_content.html");
        FileUtils.touch(headerHtmlFile);

        final Map<String, Object> parameters = DocumentUtils.convertJsonStringToMap(this.dataJson);
        final String newHeaderHtmlFileContent = HTMLUtils.modifyTemplate(parameters, this.headerTemplate);

        FileUtils.writeStringToFile(headerHtmlFile, newHeaderHtmlFileContent, StandardCharsets.UTF_8);
        importFontsInto(headerHtmlFile);

        log.info("[SERVICE] header has been generated");

        return headerHtmlFile;
    }

    private File findFooterFile(final String pathToTemplate) throws Exception {
        log.info("[SERVICE] generating footer");

        if (this.footerTemplate == null || !this.footerTemplate.exists()) {
            log.info("[SERVICE] footer file does not found, skip it");
            return null;
        }

        final File footerHtmlFile = new File(pathToTemplate + "footer_content.html");
        FileUtils.touch(footerHtmlFile);

        FileUtils.copyFile(this.footerTemplate, footerHtmlFile);
        importFontsInto(footerHtmlFile);
        insertLinkIn(footerHtmlFile);

        final String linkToPdfDocument = getLinkToPdfDocument();
        final File qrCodeImage = generateQrCodeImageFrom(linkToPdfDocument);
        HTMLUtils.insertQrCodeImagePathAndAltText(
                qrCodeImage.getAbsolutePath(), this.documentID, footerHtmlFile
        );

        log.info("[SERVICE] footer has been generated");

        return footerHtmlFile;
    }

    private void generatePDFWithWkhtmltopdf(
            final String pathToTemplate, final File document,
            final File headerHtmlFile, final File footerHtmlFile) throws Exception {
        FileUtils.touch(document);

        final String pathToPDF = document.getAbsolutePath();
        log.info("[WKHTMLTOPDF] generating PDF by path: '{}'", pathToPDF);

        final List<String> command = new ArrayList<>();
        command.add("wkhtmltopdf");
        command.add("--margin-bottom");
        command.add("42");
        command.add("--margin-left");
        command.add("0");
        command.add("--margin-right");
        command.add("0");
        command.add("--page-size");
        command.add("A4");
        if (headerHtmlFile != null) {
            command.add("--margin-top");
            command.add("85.75");
            command.add("--header-spacing");
            command.add("1");
            command.add("--header-html");
            command.add(headerHtmlFile.getAbsolutePath());
        }
        if (footerHtmlFile != null) {
            command.add("--footer-spacing");
            command.add("5");
            command.add("--footer-html");
            command.add(footerHtmlFile.getAbsolutePath());
        }
        command.add(pathToTemplate);
        command.add(pathToPDF);

        final ProcessBuilder pb = new ProcessBuilder(command);
        pb.start().waitFor();
        log.info("[WKHTMLTOPDF] PDF successfully generated by path: '{}'", pathToPDF);
    }

    private void insertLinkIn(final File footerHtmlFile) throws IOException {
        final String linkToPdfDocument = getLinkToPdfDocument();
        HTMLUtils.insertDocumentLinkIfFound(linkToPdfDocument, footerHtmlFile);
    }
}
