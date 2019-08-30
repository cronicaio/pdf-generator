package io.cronica.api.pdfgenerator.component.entity;

import lombok.Getter;

import java.nio.charset.StandardCharsets;

@Getter
public enum DocumentStatus {
    REGISTERED("REGISTERED"),
    PENDING("PENDING"),
    GENERATED("GENERATED");

    private String status;
    private byte[] rawStatus;

    DocumentStatus(final String status) {
        this.status = status;
        this.rawStatus = status.getBytes(StandardCharsets.UTF_8);
    }
}
