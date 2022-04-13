package io.cronica.api.pdfgenerator.exception;

public class DocumentPendingException extends CronicaRuntimeException {
    public DocumentPendingException(String msg) {
        super(msg);
    }

    public DocumentPendingException(String msg, Exception ex) {
        super(msg, ex);
    }
}
