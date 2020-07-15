package io.cronica.api.pdfgenerator.component.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CARequestsMetrics {

    private final static String SIGN_DOCUMENT_SUCCESSFUL_REQUESTS_COUNT = "sign_document_successful_requests";
    private final static String SIGN_DOCUMENT_FAILED_REQUESTS_COUNT = "sign_document_failed_requests";

    private final static String SIGN_DOCUMENT_REQUEST_TIME = "sign_document_time";

    private Counter signDocumentSuccessfulRequests;
    private Counter signDocumentFailedRequests;

    private Timer signDocumentTimer;

    public CARequestsMetrics(final MeterRegistry meterRegistry) {
        this.signDocumentSuccessfulRequests = Counter.builder(SIGN_DOCUMENT_SUCCESSFUL_REQUESTS_COUNT).register(meterRegistry);
        this.signDocumentFailedRequests = Counter.builder(SIGN_DOCUMENT_FAILED_REQUESTS_COUNT).register(meterRegistry);

        this.signDocumentTimer = Timer.builder(SIGN_DOCUMENT_REQUEST_TIME).publishPercentileHistogram().register(meterRegistry);
    }

    public Void incrementSignDocumentSuccessfulRequests() {
        this.signDocumentSuccessfulRequests.increment();
        return null;
    }

    public Void incrementSignDocumentFailedRequests() {
        this.signDocumentFailedRequests.increment();
        return null;
    }

    public void recordSignDocumentExecTime(final long executionTime) {
        this.signDocumentTimer.record(executionTime, TimeUnit.MILLISECONDS);
    }
}
