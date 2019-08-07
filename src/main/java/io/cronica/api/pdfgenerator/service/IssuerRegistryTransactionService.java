package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.entity.Issuer;

import java.util.List;

public interface IssuerRegistryTransactionService {

    /**
     * Read all issuers from smart-contract.
     *
     * @return list of {@link Issuer} objects
     */
    List<Issuer> getIssuers();

    /**
     * Read info about issuer from smart-contract by specified unique code of bank.
     *
     * @param bankCode
     *          - unique code of the bank
     * @return {@link Issuer} object
     */
    Issuer getIssuerByBankCode(String bankCode);
}
