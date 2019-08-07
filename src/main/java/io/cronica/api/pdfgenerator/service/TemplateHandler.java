package io.cronica.api.pdfgenerator.service;

import java.io.IOException;
import java.io.InputStream;

public interface TemplateHandler {

    /**
     * Generate template file which will be used for generating PDF document.
     *
     * @throws IOException - if exception happens while working with files
     */
    void generateTemplate() throws IOException;

    /**
     * Download images and fonts from AWS S3 Bucket to local filesystem.
     */
    void downloadAdditionalFiles();

    /**
     * Generate PDF document from template.
     *
     * @return PDF document file as {@link InputStream} object
     *
     * @throws IOException - if exception happens while working with files
     * @throws InterruptedException - if exception happens while generating PDF document
     */
    InputStream generatePDFDocument() throws Exception;
}
