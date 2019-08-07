package io.cronica.api.pdfgenerator.exception;

public class QuorumTransactionException extends CronicaRuntimeException {

    public QuorumTransactionException(String msg) {
        super(msg);
    }

    public QuorumTransactionException(String msg, Exception ex) {
        super(msg, ex);
    }
}
