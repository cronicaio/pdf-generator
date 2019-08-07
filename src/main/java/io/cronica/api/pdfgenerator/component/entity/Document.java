package io.cronica.api.pdfgenerator.component.entity;

import lombok.Getter;

@Getter
public class Document {

    private String fileName;

    private byte[] file;

    public static Document newInstance(final String fileName, final byte[] file) {
        final Document document = new Document();
        document.fileName = fileName;
        document.file = file;

        return document;
    }
}
