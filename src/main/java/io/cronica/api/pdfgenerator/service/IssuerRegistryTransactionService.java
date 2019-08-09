package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.entity.Issuer;

public interface IssuerRegistryTransactionService {

    /**
     * Read info about issuer from smart-contract by specified unique code of bank.
     *
     * @param bankCode
     *          - unique code of the bank
     * @return {@link Issuer} object
     */
    Issuer getIssuerByBankCode(String bankCode);
}
