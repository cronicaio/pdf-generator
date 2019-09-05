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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.cronica.api.pdfgenerator.utils.Constants.*;
import static io.cronica.api.pdfgenerator.utils.Constants.DEFAULT_FOOTER_CONTENT_FILE_PATH;

@Slf4j
@RequiredArgsConstructor
public class HtmlToPdfConverterService {

//    private final ChromeService service;
//
//    private final Repeater repeater;
//
//    private final AWSS3BucketAdapter awss3BucketAdapter;
//
//    private final TemplateTransactionService transactionService;
//
//    private final DocumentTransactionService documentTransactionService;
//
//    private final IssuerRegistryTransactionService issuerService;
//
//    public InputStream generatePDFDocument(final DocumentCertificate documentCertificate) throws Exception {
//        final String documentAddress = DocumentUtils.readDocumentAddress(documentCertificate.getDocumentID());
//        final String templateId = this.documentTransactionService.getTemplateID(documentAddress);
//        final TemplateContract templateContract = this.transactionService.loadTemplate(templateId);
//
//        final String contentTemplate = downloadMainContent(templateContract);
//        final Optional<String> headerTemplate = downloadHeaderContent(templateContract);
//        final Optional<String> footerTemplate = downloadFooterContent(templateContract);
//
//        downloadAdditionalFiles(templateId);
//
//
//
//        return null;
//    }
//
//    private InputStream requestToChrome(@NonNull final String content, @Nullable final String header, @Nullable final String footer) {
//        final ChromeTab tab = service.createTab();
//        final ChromeDevToolsService devToolsService = service.createDevToolsService(tab);
//        final Page page = devToolsService.getPage();
//        page.setDocumentContent(tab.getId(), content);
//        final String pdfBase64 = page.printToPDF(
//                false,
//                true, // TODO: Need to check
//                true,
//                .0,
//                8.3,
//                11.7,
//                3.4645669,  // TODO: Need to check
//                1.65354,  // TODO: Need to check
//                .0,
//                .0,
//                "",
//                false,
//                "",  // TODO: Need to check
//                "",  // TODO: Need to check
//                false,
//                PrintToPDFTransferMode.RETURN_AS_BASE_64
//        ).getData();
//        devToolsService.waitUntilClosed();
//    }
//
//    private void downloadAdditionalFiles(String templateID) {
//        final String imagesFolder = "template/" + templateID + "/";
//        final String fontsFolder = "template/" + templateID + "/fonts/";
//
//        String s3Prefix = "template/" + templateID + "/img/";
//        this.repeater.apply(this.awss3BucketAdapter::downloadFilesWithPrefix, s3Prefix, imagesFolder);
//
//        s3Prefix = "template/" + templateID + "/fonts/";
//        this.repeater.apply(this.awss3BucketAdapter::downloadFilesWithPrefix, s3Prefix, fontsFolder);
//    }
//
//    private String downloadMainContent(final TemplateContract templateContract) {
//        return this.transactionService.getMainContentOfTemplate(templateContract);
//    }
//
//    private Optional<String> downloadHeaderContent(final TemplateContract templateContract) {
//        final String content = this.transactionService.getHeaderContentOfTemplate(templateContract);
//        if ( !StringUtils.isEmpty(content) ) {
//            return Optional.of(content);
//        }
//        return Optional.empty();
//    }
//
//    private Optional<String> downloadFooterContent(TemplateContract templateContract) {
//        final String content = this.transactionService.getFooterContentOfTemplate(templateContract);
//        if ( !StringUtils.isEmpty(content) ) {
//            return Optional.of(content);
//        }
//        return Optional.empty();
//    }
//
//    private String prepareTemplateForGenerating(final String template, final String dataJson) throws IOException {
//        final Map<String, Object> parameters = DocumentUtils.convertJsonStringToMap(dataJson);
//        return HTMLUtils.modifyTemplate(parameters, template);
//    }
//
//    private void importFontsInto(final String templateWithData) throws IOException {
//        final List<Font> fontEntityList = HTMLUtils.readFonts("template/" + this.templateID + "/fonts/");
//        HTMLUtils.importFontsIntoHtml(templateWithData, fontEntityList);
//    }
//
//    private void insertQrCodeIfNeeded() throws Exception {
//        final boolean qrCodeImageTagsFound = HTMLUtils.findQRCodeImageTags(this.template);
//        if (qrCodeImageTagsFound) {
//            final String linkToPdfDocument = getLinkToPdfDocument();
//            final File qrCodeImage = generateQrCodeImageFrom(linkToPdfDocument);
//
//            HTMLUtils.insertQrCodeImagePathAndAltText(
//                    qrCodeImage.getAbsolutePath(), linkToPdfDocument, this.template
//            );
//        }
//    }
//
//    private String getLinkToPdfDocument(String bankCode, String documentID) throws IssuerNotFoundException {
//        final Issuer issuer = this.issuerService.getIssuerByBankCode(bankCode);
//
//        return issuer.getFrontEndLink()
//                + SEARCH_BY_ID_STRUCTURED_FRONTEND_BASIC_URL
//                + documentID;
//    }
//
//    private BufferedImage generateQrCodeImageFrom(final String linkToPdfDocument) {
//        return DocumentUtils.generateQRCodeImage(
//                linkToPdfDocument,
//                100,
//                100);
//    }
//
//    private File findHeaderFile(final String pathToTemplate) throws IOException {
//        log.info("[SERVICE] generating header");
//
//        if (this.headerTemplate == null || !this.headerTemplate.exists()) {
//            log.info("[SERVICE] header template file does not found, skip it");
//            return null;
//        }
//
//        final File headerHtmlFile = new File(pathToTemplate + "header_content.html");
//        FileUtils.touch(headerHtmlFile);
//
//        final Map<String, Object> parameters = DocumentUtils.convertJsonStringToMap(this.dataJson);
//        final String newHeaderHtmlFileContent = HTMLUtils.modifyTemplate(parameters, this.headerTemplate);
//
//        FileUtils.writeStringToFile(headerHtmlFile, newHeaderHtmlFileContent, StandardCharsets.UTF_8);
//        importFontsInto(headerHtmlFile);
//
//        log.info("[SERVICE] header has been generated");
//
//        return headerHtmlFile;
//    }
//
//    private File findFooterFile(final String pathToTemplate) throws Exception {
//        log.info("[SERVICE] generating footer");
//
//        if (this.footerTemplate == null || !this.footerTemplate.exists()) {
//            this.footerTemplate = new File(DEFAULT_FOOTER_CONTENT_FILE_PATH);
//            if (!this.footerTemplate.exists()) {
//                log.info("[SERVICE] footer file does not found, skip it");
//                return null;
//            }
//        }
//
//        final File footerHtmlFile = new File(pathToTemplate + "footer_content.html");
//        FileUtils.touch(footerHtmlFile);
//
//        FileUtils.copyFile(this.footerTemplate, footerHtmlFile);
//        importFontsInto(footerHtmlFile);
//        insertLinkIn(footerHtmlFile);
//
//        final String linkToPdfDocument = getLinkToPdfDocument();
//        final File qrCodeImage = generateQrCodeImageFrom(linkToPdfDocument);
//        HTMLUtils.insertQrCodeImagePathAndAltText(
//                qrCodeImage.getAbsolutePath(), this.documentID, footerHtmlFile
//        );
//
//        log.info("[SERVICE] footer has been generated");
//
//        return footerHtmlFile;
//    }
//
//    private void insertLinkIn(final File footerHtmlFile) throws IOException {
//        final String linkToPdfDocument = getLinkToPdfDocument();
//        HTMLUtils.insertDocumentLinkIfFound(linkToPdfDocument, footerHtmlFile);
//    }
}
