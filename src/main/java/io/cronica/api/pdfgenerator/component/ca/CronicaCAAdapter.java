package io.cronica.api.pdfgenerator.component.ca;

import java.io.InputStream;

public interface CronicaCAAdapter {

    /**
     * Sign document on the Cronica CA service.
     *
     * @param documentInputStream
     *              - document as {@link InputStream} object
     * @return signed document as {@link InputStream} object
     */
    InputStream signDocument(InputStream documentInputStream);
}
