package io.cronica.api.pdfgenerator.service;

import org.web3j.tx.Contract;

public interface DocumentTransactionService {

    /**
     * Load document from Quorum network in object-wrapper.
     *
     * @param contractType
     *          - type of the contract that should be loaded
     * @param documentAddress
     *          - address of document in Quorum network
     * @return wrapper of smart-contract
     */
    <T extends Contract> T loadDocument(Class<T> contractType, String documentAddress);

    /**
     * Returns code of the bank that issued document with specified document address.
     *
     * @param documentAddress
     *          - address of smart-contract with information about document
     */
    String getBankCode(String documentAddress);

    /**
     * Retrieve hash of document of smart-contract.
     *
     * @param documentAddress
     *          - address of smart-contract
     * @return {@link String} hash retrieved from smart-contract
     */
    String getHash(String documentAddress);

    /**
     * Get address of smart-contract used as template by document with specified address.
     *
     * @param documentAddress
     *          - address of smart-contract with information about document
     * @return {@link String} address of smart-contract
     */
    String getTemplateID(String documentAddress);

    /**
     * Get structured data from smart-contract.
     *
     * @param documentAddress
     *          - address of smart-contract with information about document
     * @return {@link String} structured data as JSON string
     */
    String getStructuredData(String documentAddress);
}
