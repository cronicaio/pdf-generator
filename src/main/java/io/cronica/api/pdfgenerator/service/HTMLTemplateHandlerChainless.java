package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.utils.ArchiveUtility;
import io.cronica.api.pdfgenerator.utils.DocumentUtils;
import io.cronica.api.pdfgenerator.utils.FileUtility;
import io.cronica.api.pdfgenerator.utils.HTMLUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static io.cronica.api.pdfgenerator.utils.Constants.*;

@Slf4j
public final class HTMLTemplateHandlerChainless implements TemplateHandler {

    private static final String TEMPLATE_PREVIEW = "TEMPLATE_PREVIEW";

    private final UUID tempUid;

    private File template;

    private File footerTemplate;

    private File headerTemplate;

    private ByteBuffer buffer;

    public HTMLTemplateHandlerChainless(final byte[] templateBytes) {
        this.tempUid = UUID.randomUUID();
        this.buffer = ByteBuffer.wrap(templateBytes);
    }

    public HTMLTemplateHandlerChainless(final ByteBuffer templateBuffer) {
        this.tempUid = UUID.randomUUID();
        this.buffer = templateBuffer;
    }

    /**
     * @see TemplateHandler#generateTemplate()
     */
    @Override
    public void generateTemplate() {
        final Set<File> files = new HashSet<>(ArchiveUtility.decompressZip(this.buffer, "./temp/" + tempUid.toString() + "/"));
        this.template = FileUtility.validateTemplateFile(files);
        this.headerTemplate = FileUtility.validateHeaderFile(files);
        this.footerTemplate = FileUtility.validateFooterFile(files);
    }

    /**
     * @see TemplateHandler#downloadAdditionalFiles()
     */
    @Override
    public void downloadAdditionalFiles() {
        /* Ignored */
    }

    /**
     * @see TemplateHandler#generatePDFDocument()
     */
    @Override
    public InputStream generatePDFDocument() throws Exception {
        final String fileName = "DC-" + this.tempUid.toString() + ".pdf";
        final File document = new File(PATH_TO_PDF_DOCUMENTS + "/" + fileName);

        try {
            log.info("[SERVICE] begin generating a PDF document using HTML template");
            final File preparedTemplate = prepareTemplateForGenerating();
            insertQrCodeIfNeeded();
            final File headerFile = findHeaderFile();
            final File footerFile = findFooterFile();

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

            log.info("[SERVICE] successfully generated PDF preview template document");
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

    private File prepareTemplateForGenerating() throws IOException {
        final Map<String, Object> parameters = DocumentUtils.convertJsonStringToMap("{}");
        final String newTemplateContent = HTMLUtils.modifyTemplate(parameters, this.template);

        final File document = new File("./template/" + this.tempUid.toString() + "/template.html");
        FileUtils.touch(document);

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(document))) {
            writer.write(String.valueOf(newTemplateContent));
        }

        return document;
    }

    private void insertQrCodeIfNeeded() {
        try {
            final boolean qrCodeImageTagsFound = HTMLUtils.findQRCodeImageTags(this.template);
            if (qrCodeImageTagsFound) {
                final File qrCodeImage = generateQrCodeImageFrom();

                HTMLUtils.insertQrCodeImagePathAndAltText(
                        qrCodeImage.getAbsolutePath(), TEMPLATE_PREVIEW, this.template
                );
            }
        } catch (Exception e) {
            log.error("[SERVICE] Error inserting QR code image", e);
        }
    }

    private File generateQrCodeImageFrom() {
        final File qrCodeImage = new File(
                PATH_TO_FOLDER_WITH_QR_CODE
                        + "QR-" + this.tempUid.toString() + PNG_FILE_EXTENSION);
        DocumentUtils.generateQRCodeImage(
                TEMPLATE_PREVIEW,
                100,
                100,
                qrCodeImage.getAbsolutePath());
        return qrCodeImage;
    }

    private File findHeaderFile() throws IOException {
        log.info("[SERVICE] generating header");

        if (this.headerTemplate == null || !this.headerTemplate.exists()) {
            log.info("[SERVICE] header template file does not found, skip it");
            return null;
        }

        final File headerHtmlFile = new File("./template/" + this.tempUid.toString() + "/header_content.html");
        FileUtils.touch(headerHtmlFile);

        final Map<String, Object> parameters = DocumentUtils.convertJsonStringToMap("{}");
        final String newHeaderHtmlFileContent = HTMLUtils.modifyTemplate(parameters, this.headerTemplate);

        FileUtils.writeStringToFile(headerHtmlFile, newHeaderHtmlFileContent, StandardCharsets.UTF_8);

        log.info("[SERVICE] header has been generated");

        return headerHtmlFile;
    }

    private File findFooterFile() throws Exception {
        log.info("[SERVICE] generating footer");

        if (this.footerTemplate == null || !this.footerTemplate.exists()) {
            this.footerTemplate = new File(DEFAULT_FOOTER_CONTENT_FILE_PATH);
            if (!this.footerTemplate.exists()) {
                log.info("[SERVICE] footer file does not found, skip it");
                return null;
            }
        }

        final File footerHtmlFile = new File("./template/" + this.tempUid.toString() + "/footer_content.html");
        FileUtils.touch(footerHtmlFile);

        FileUtils.copyFile(this.footerTemplate, footerHtmlFile);
        insertLinkIn(footerHtmlFile);

        final File qrCodeImage = generateQrCodeImageFrom();
        HTMLUtils.insertQrCodeImagePathAndAltText(
                qrCodeImage.getAbsolutePath(), TEMPLATE_PREVIEW, footerHtmlFile
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
            command.add("1");
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
        HTMLUtils.insertDocumentLinkIfFound(TEMPLATE_PREVIEW, footerHtmlFile);
    }
}
