package io.cronica.api.pdfgenerator.service;

import com.github.kklisura.cdt.protocol.commands.Page;
import com.github.kklisura.cdt.protocol.types.page.PrintToPDFTransferMode;
import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.types.ChromeTab;
import io.cronica.api.pdfgenerator.component.aws.AWSS3BucketAdapter;
import io.cronica.api.pdfgenerator.component.aws.Repeater;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.cronica.api.pdfgenerator.utils.Constants.*;

@Slf4j
@Scope("prototype")
public class HTMLTemplateHandlerChrome implements TemplateHandler  {

    private static final double A4_PAPER_HEIGHT = 11.7;
    private static final double A4_PAPER_WIDTH = 8.3;

    private final String bankCode;

    private final String dataJson;

    private final String documentID;

    private final String templateID;

    private final Repeater repeater;

    private final AWSS3BucketAdapter awss3BucketAdapter;

    private final TemplateTransactionService transactionService;

    private final IssuerRegistryTransactionService issuerService;

    private final ChromeService service;

    private File template;

    private File footerTemplate;

    private File headerTemplate;

    private TemplateContract templateContract;

    public HTMLTemplateHandlerChrome(
            Repeater repeater,
            AWSS3BucketAdapter awss3BucketAdapter,
            DocumentCertificate docCert,
            IssuerRegistryTransactionService issuerService,
            TemplateTransactionService templateTransactionService,
            DocumentTransactionService documentTransactionService,
            ChromeService service
    ) {
        this.repeater = repeater;
        this.awss3BucketAdapter = awss3BucketAdapter;
        this.issuerService = issuerService;
        this.transactionService = templateTransactionService;
        this.service = service;

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

        log.info("[SERVICE] begin generating a PDF document using HTML template");
        final File preparedTemplate = prepareTemplateForGenerating(uuid);
        importFontsInto(preparedTemplate);
        insertQrCodeIfNeeded();
        final File headerFile = findHeaderFile(preparedTemplate.getAbsolutePath());
        final File footerFile = findFooterFile(preparedTemplate.getAbsolutePath());

        InputStream inputStream = requestToChrome(preparedTemplate.getAbsolutePath(), headerFile, footerFile);

        deleteFile(preparedTemplate);
        if (headerFile != null && headerFile.exists()) {
            deleteFile(headerFile);
        }
        if (footerFile != null && footerFile.exists()) {
            deleteFile(footerFile);
        }

        log.info("[SERVICE] successfully generated PDF document with '{}' ID, using HTML", this.documentID);
        return inputStream;
    }

    private InputStream requestToChrome(
            final String pathToTemplate,
            final File headerHtmlFile,
            final File footerHtmlFile
    ) throws IOException {
        final ChromeTab tab = service.createTab();
        final ChromeDevToolsService devToolsService = service.createDevToolsService(tab);
        final Page page = devToolsService.getPage();

        double marginTop = .4;
        boolean displayHeaderFooter = false;

        final String content = FileUtils.readFileToString(new File(pathToTemplate), StandardCharsets.UTF_8);

        String header = "<span></span>";
        if (headerHtmlFile != null) {
            marginTop = 3.3759843; //TODO: Get value from template property
            header = FileUtils.readFileToString(headerHtmlFile, StandardCharsets.UTF_8);
            displayHeaderFooter = true;
        }

        String footer = "<span></span>";
        if (footerHtmlFile != null) {
            //marginBottom TODO: Get value from template property
            footer = FileUtils.readFileToString(footerHtmlFile, StandardCharsets.UTF_8);
            displayHeaderFooter = true;
        }

        page.setDocumentContent(tab.getId(), content);
        final String pdfBase64 = page.printToPDF(
                false,
                displayHeaderFooter,
                true,
                1.0,
                A4_PAPER_WIDTH,
                A4_PAPER_HEIGHT,
                marginTop,
                1.65354,
                .0,
                .0,
                "",
                false,
                header,
                footer,
                false,
                PrintToPDFTransferMode.RETURN_AS_BASE_64
        ).getData();
        devToolsService.close();
        devToolsService.waitUntilClosed();

        return new ByteArrayInputStream(Base64.getDecoder().decode(pdfBase64));
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
            final String qrCodeImageBase64 = new String(Base64.getMimeEncoder().encode(generateQrCodeImageFrom(linkToPdfDocument)), StandardCharsets.UTF_8);

            HTMLUtils.insertQrCodeImageBase64(
                    qrCodeImageBase64, linkToPdfDocument, "image/png", this.template
            );
        }
    }

    private String getLinkToPdfDocument() throws IssuerNotFoundException {
        final Issuer issuer = this.issuerService.getIssuerByBankCode(this.bankCode);

        return issuer.getFrontEndLink()
                + SEARCH_BY_ID_STRUCTURED_FRONTEND_BASIC_URL
                + this.documentID;
    }

    private byte[] generateQrCodeImageFrom(final String linkToPdfDocument) throws IOException {
        BufferedImage originalImage = DocumentUtils.generateQRCodeImage(
                linkToPdfDocument,
                100,
                100);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos);
        baos.flush();
        return baos.toByteArray();
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
            this.footerTemplate = new File(DEFAULT_FOOTER_CONTENT_FILE_PATH);
            if (!this.footerTemplate.exists()) {
                log.info("[SERVICE] footer file does not found, skip it");
                return null;
            }
        }

        final File footerHtmlFile = new File(pathToTemplate + "footer_content.html");
        FileUtils.touch(footerHtmlFile);

        FileUtils.copyFile(this.footerTemplate, footerHtmlFile);
        importFontsInto(footerHtmlFile);
        insertLinkIn(footerHtmlFile);

        final String linkToPdfDocument = getLinkToPdfDocument();
        final String qrCodeImageBase64 = new String(Base64.getMimeEncoder().encode(generateQrCodeImageFrom(linkToPdfDocument)), StandardCharsets.UTF_8);
        HTMLUtils.insertQrCodeImageBase64(
                qrCodeImageBase64, this.documentID, "image/png", footerHtmlFile
        );

        log.info("[SERVICE] footer has been generated");

        return footerHtmlFile;
    }



    private void insertLinkIn(final File footerHtmlFile) throws IOException {
        final String linkToPdfDocument = getLinkToPdfDocument();
        HTMLUtils.insertDocumentLinkIfFound(linkToPdfDocument, footerHtmlFile);
    }

}
