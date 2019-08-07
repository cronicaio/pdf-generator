package io.cronica.api.pdfgenerator.component.exception.unchecked;

public class CronicaRuntimeException extends RuntimeException {

    public CronicaRuntimeException(String msg) {
        super(msg);
    }

    public CronicaRuntimeException(String msg, Exception ex) {
        super(msg + " because of " + ex.toString());
    }

}
