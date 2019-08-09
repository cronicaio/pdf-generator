package io.cronica.api.pdfgenerator.exception;

import lombok.Getter;

@Getter
public enum  ErrorCode {

    INVALID_REQUEST(40010, "bad_request", "Please, check information you sent"),
    DOCUMENT_NOT_FOUND(40410, "not_found", "Document does not exists"),
    ISSUER_NOT_FOUND(42210, "unprocessable_entity", "Document contains not relevant data"),
    INTERNAL_SERVER_ERROR(50010, "internal_server_error", "Exception on server"),
    QUORUM_NODE_EXCEPTION(50210, "bad_gateway", "Exception on server");

    private int code;

    private String errorType;

    private String description;

    ErrorCode(int code, String errorType, String description) {
        this.code = code;
        this.errorType = errorType;
        this.description = description;
    }
}
