package io.cronica.api.pdfgenerator.component.exception.unchecked;

public class InvalidRequestException extends CronicaRuntimeException {

    public InvalidRequestException(String msg) {
        super(msg);
    }

    public InvalidRequestException(String msg, Exception ex) {
        super(msg, ex);
    }

}
