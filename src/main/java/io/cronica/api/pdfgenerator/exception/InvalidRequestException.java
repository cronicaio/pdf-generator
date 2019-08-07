package io.cronica.api.pdfgenerator.exception;

public class InvalidRequestException extends CronicaRuntimeException {

    public InvalidRequestException(String msg) {
        super(msg);
    }

    public InvalidRequestException(String msg, Exception ex) {
        super(msg, ex);
    }
}
