package io.cronica.api.pdfgenerator.exception;

public class DocumentNotFoundException extends CronicaRuntimeException {

    public DocumentNotFoundException(String msg) {
        super(msg);
    }

    public DocumentNotFoundException(String msg, Exception ex) {
        super(msg, ex);
    }
}
