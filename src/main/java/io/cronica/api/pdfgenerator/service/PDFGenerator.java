package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.kafka.entities.GeneratePdfRequest;

import java.time.Duration;

public interface PDFGenerator {

    Duration TIME_TO_LIVE_PREVIEW_CACHE = Duration.ofMinutes(5);
    Duration TIME_TO_LIVE = Duration.ofHours(24);

    /**
     * Generate PDF document with specified ID, encrypt it and save to Redis.
     *
     * @param request
     *          - request data for PDF generation
     */
    void generateAndSave(GeneratePdfRequest request);
}
