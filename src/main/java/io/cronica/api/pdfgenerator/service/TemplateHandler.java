package io.cronica.api.pdfgenerator.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface TemplateHandler {

    int QR_CODE_IMAGE_WIDTH = 200;
    int QR_CODE_IMAGE_HEIGHT = 200;

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
     * @return PDF document file as byte array object
     *
     * @throws IOException - if exception happens while working with files
     * @throws InterruptedException - if exception happens while generating PDF document
     */
    byte[] generatePDFDocument() throws Exception;
}
