package io.cronica.api.pdfgenerator.component.dto;

import io.cronica.api.pdfgenerator.exception.ErrorCode;
import lombok.Getter;

@Getter
public class APIErrorResponseDTO {

    private String error;

    private String error_code;

    private String error_description;

    public APIErrorResponseDTO(final ErrorCode error) {
        this.error = error.getErrorType();
        this.error_code = String.valueOf(error.getCode());
        this.error_description = error.getDescription();
    }
}
