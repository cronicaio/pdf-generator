package io.cronica.api.pdfgenerator.service;

import javax.annotation.Nullable;
import java.time.Duration;

public interface StructuredPDFGenerator {

    Duration TIME_TO_LIVE_PREVIEW_CACHE = Duration.ofMinutes(5);

    /**
     * Generate PDF document with specified ID, encrypt it and save to Redis.
     *
     * @param documentID
     *          - unique ID of document
     */
    void generateAndSave(String documentID, @Nullable String data);
}
