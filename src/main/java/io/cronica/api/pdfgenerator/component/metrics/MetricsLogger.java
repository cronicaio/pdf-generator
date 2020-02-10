package io.cronica.api.pdfgenerator.component.metrics;

public interface MetricsLogger {

    /**
     * Increment counter metric, identified by its method's ID.
     *
     * @param methodID
     *          - ID of the method
     */
    void incrementCount(MethodID methodID);

    /**
     * Record execution time of the request, identified by its method's ID.
     *
     * @param methodID
     *          - ID of the method
     * @param executionTime
     *          - time while function were executing
     */
    void logExecutionTime(MethodID methodID, long executionTime);
}
