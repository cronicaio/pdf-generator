package io.cronica.api.pdfgenerator.exception;

public class IssuerNotFoundException extends CronicaRuntimeException {

    public IssuerNotFoundException(String msg) {
        super(msg);
    }

    public IssuerNotFoundException(String msg, Exception ex) {
        super(msg, ex);
    }
}
