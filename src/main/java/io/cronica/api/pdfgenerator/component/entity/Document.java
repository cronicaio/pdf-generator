package io.cronica.api.pdfgenerator.component.entity;

import lombok.Getter;

import java.io.InputStream;

@Getter
public class Document {

    private String fileName;

    private InputStream file;

    public static Document newInstance(final String fileName, final InputStream file) {
        final Document document = new Document();
        document.fileName = fileName;
        document.file = file;

        return document;
    }
}
