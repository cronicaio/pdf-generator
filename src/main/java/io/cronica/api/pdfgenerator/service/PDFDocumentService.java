package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.entity.Document;

public interface PDFDocumentService {

    /**
     * Generate PDF document by ID stored in Redis DB, identified by specified UUID.
     *
     * @param uuid
     *          - ID of document in Redis DB
     * @return {@link Document} object
     */
    Document generatePDFDocument(String uuid);

    /**
     * Generate example PDF document by given template id aka contract address
     *
     * @param templateAddress
     *          - template contract address
     * @return {@link Document} object
     */
    Document generateExampleDocument(String templateAddress);
}
