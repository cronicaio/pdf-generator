package io.cronica.api.pdfgenerator.service;

public interface StructuredPDFGenerator {

    /**
     * Generate PDF document with specified ID, encrypt it and save to Redis.
     *
     * @param documentID
     *          - unique ID of document
     */
    void generateAndSave(String documentID);
}
