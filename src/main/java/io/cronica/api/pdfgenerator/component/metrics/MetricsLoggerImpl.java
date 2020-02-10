package io.cronica.api.pdfgenerator.component.metrics;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class MetricsLoggerImpl implements MetricsLogger {

    /* mapping of method IDs to its {@link java.util.function.Supplier} implementation */
    private static final ConcurrentHashMap<MethodID, Supplier<Void>> methodIDToSupplier = new ConcurrentHashMap<>();

    /* mapping of method IDs to its {@link java.util.function.Consumer} implementation */
    private static final ConcurrentHashMap<MethodID, Consumer<Long>> methodIDToConsumer = new ConcurrentHashMap<>();

    public MetricsLoggerImpl(final CARequestsMetrics caRequestsMetrics, final DocumentMetrics documentMetrics) {
        methodIDToSupplier.put(MethodID.SIGN_DOCUMENT_SUCCESSFUL_REQUESTS, caRequestsMetrics::incrementSignDocumentSuccessfulRequests);
        methodIDToSupplier.put(MethodID.SIGN_DOCUMENT_FAILED_REQUESTS, caRequestsMetrics::incrementSignDocumentFailedRequests);
        methodIDToSupplier.put(MethodID.COUNT_OF_SUCCESSFUL_DOCUMENT_GENERATIONS, documentMetrics::incrementCountOfSuccessfulDocumentGeneration);
        methodIDToSupplier.put(MethodID.COUNT_OF_FAILED_DOCUMENT_GENERATIONS, documentMetrics::incrementCountOfFailedDocumentGeneration);

        methodIDToConsumer.put(MethodID.SIGN_DOCUMENT_REQUEST_TIME, caRequestsMetrics::recordSignDocumentExecTime);
        methodIDToConsumer.put(MethodID.TIME_OF_DOCUMENT_GENERATION, documentMetrics::recordTimeOfDocumentGenerationExecTime);
    }

    /**
     * @see MetricsLogger#incrementCount(MethodID)
     */
    @Override
    public void incrementCount(final MethodID methodID) {
        final Supplier<Void> supplier = methodIDToSupplier.get(methodID);
        if (supplier != null) {
            supplier.get();
        }
    }

    /**
     * @see MetricsLogger#logExecutionTime(MethodID, long)
     */
    @Override
    public void logExecutionTime(final MethodID methodID, final long executionTime) {
        final Consumer<Long> consumer = methodIDToConsumer.get(methodID);
        if (consumer != null) {
            consumer.accept(executionTime);
        }
    }
}
