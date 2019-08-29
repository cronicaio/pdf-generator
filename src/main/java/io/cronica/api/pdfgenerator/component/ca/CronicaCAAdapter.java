package io.cronica.api.pdfgenerator.component.ca;

public interface CronicaCAAdapter {

    /**
     * Sign document on the Cronica CA service.
     *
     * @param documentBytes
     *              - document as array of bytes
     * @return signed document as array of bytes
     */
    byte[] signDocument(byte[] documentBytes);
}
