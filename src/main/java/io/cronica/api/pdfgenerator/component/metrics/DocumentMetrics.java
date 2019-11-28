package io.cronica.api.pdfgenerator.component.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DocumentMetrics {

    private static final String COUNT_OF_SUCCESSFUL_DOCUMENT_GENERATIONS = "count_of_successful_document_generations";
    private static final String COUNT_OF_FAILED_DOCUMENT_GENERATIONS = "count_of_failed_document_generations";

    private static final String TIME_OF_DOCUMENT_GENERATION = "time_of_document_generation";

    private Counter countOfSuccessfulDocumentGenerations;
    private Counter countOfFailedDocumentGenerations;

    private Timer timeOfDocumentGeneration;

    public DocumentMetrics(final MeterRegistry meterRegistry) {
        this.countOfSuccessfulDocumentGenerations = Counter.builder(COUNT_OF_SUCCESSFUL_DOCUMENT_GENERATIONS).register(meterRegistry);
        this.countOfFailedDocumentGenerations = Counter.builder(COUNT_OF_FAILED_DOCUMENT_GENERATIONS).register(meterRegistry);
        this.timeOfDocumentGeneration = Timer.builder(TIME_OF_DOCUMENT_GENERATION).publishPercentileHistogram().register(meterRegistry);
    }

    public Void incrementCountOfSuccessfulDocumentGeneration() {
        this.countOfSuccessfulDocumentGenerations.increment();
        return null;
    }

    public Void incrementCountOfFailedDocumentGeneration() {
        this.countOfFailedDocumentGenerations.increment();
        return null;
    }

    public void recordTimeOfDocumentGenerationExecTime(final long executionTime) {
        this.timeOfDocumentGeneration.record(executionTime, TimeUnit.MILLISECONDS);
    }

}
