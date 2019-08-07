package io.cronica.api.pdfgenerator.exception;

public class CronicaRuntimeException extends RuntimeException {

    public CronicaRuntimeException(String msg) {
        super(msg);
    }

    public CronicaRuntimeException(String msg, Exception ex) {
        super(msg, ex);
    }
}
