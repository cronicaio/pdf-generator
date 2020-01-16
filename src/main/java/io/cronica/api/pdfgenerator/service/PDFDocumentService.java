package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.entity.Document;
import org.springframework.core.io.buffer.DataBuffer;

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

    /**
     * Generate preview PDF document by given template id aka contract address
     *
     * @param templateAddress
     *          - template contract address
     * @param jsonData
     *          - custom json data that represents a document
     * @return {@link Document} object
     */
    Document generatePreviewDocument(String templateAddress, String jsonData);

    /**
     * Generate preview PDF document by given template id aka contract address
     *
     * @param dataBuffer
     *          - data buffer with template zip content
     * @return {@link Document} object
     */
    Document generatePreviewTemplate(DataBuffer dataBuffer);
}
