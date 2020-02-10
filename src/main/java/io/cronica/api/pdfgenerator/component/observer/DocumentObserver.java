package io.cronica.api.pdfgenerator.component.observer;

import io.cronica.api.pdfgenerator.component.entity.DocumentStatus;
import io.cronica.api.pdfgenerator.component.kafka.entities.GeneratePdfRequest;

import java.util.Optional;

public interface DocumentObserver {

    void sendGeneratePdfRequest(GeneratePdfRequest request);

    /**
     * Get status of document.
     *
     * @param requestId
     *          - unique ID of document
     * @return {@link DocumentStatus} object with current status of request ID.
     */
    Optional<DocumentStatus> check(String requestId);
}
