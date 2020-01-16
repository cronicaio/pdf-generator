package io.cronica.api.pdfgenerator.service;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;

public interface StructuredPDFGenerator {

    /**
     * Generate PDF document with specified ID, encrypt it and save to Redis.
     *
     * @param documentID
     *          - unique ID of document
     */
    void generateAndSave(String documentID, @Nullable String data);

    /**
     * Generate PDF document using zipped template.
     *
     * @param templateZip
     *          - zip archive of template contents
     */
    byte[] generate(ByteBuffer templateZip);
}
