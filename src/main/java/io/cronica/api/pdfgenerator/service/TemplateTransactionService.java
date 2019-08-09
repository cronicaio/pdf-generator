package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.wrapper.TemplateContract;

public interface TemplateTransactionService {

    /**
     * Load TemplateContract with given address of contract.
     *
     * @param contractAddress
     *          - address of the smart-contract
     * @return {@link TemplateContract} object
     */
    TemplateContract loadTemplate(String contractAddress);

    /**
     * Return main content of template.
     *
     * @param templateContract
     *          - {@link TemplateContract} wrapper of smart-contract
     * @return main content of template
     */
    String getMainContentOfTemplate(TemplateContract templateContract);

    /**
     * Return header content of template.
     *
     * @param templateContract
     *          - {@link TemplateContract} wrapper of smart-contract
     * @return header content of template
     */
    String getHeaderContentOfTemplate(TemplateContract templateContract);

    /**
     * Return footer content of template.
     *
     * @param templateContract
     *          - {@link TemplateContract} wrapper of smart-contract
     * @return footer content of template
     */
    String getFooterContentOfTemplate(TemplateContract templateContract);

    /**
     * Return type of file from smart-contract.
     *
     * @param templateContract
     *          - {@link TemplateContract} wrapper of smart-contract
     * @return type of file
     */
    String getFileTypeOfTemplate(TemplateContract templateContract);
}
