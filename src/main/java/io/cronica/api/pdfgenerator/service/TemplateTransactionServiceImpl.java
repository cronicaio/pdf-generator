package io.cronica.api.pdfgenerator.service;

import static io.cronica.api.pdfgenerator.utils.Constants.GAS_LIMIT;
import static io.cronica.api.pdfgenerator.utils.Constants.GAS_PRICE;

import io.cronica.api.pdfgenerator.component.wrapper.TemplateContract;
import io.cronica.api.pdfgenerator.exception.QuorumTransactionException;
import io.cronica.api.pdfgenerator.utils.LZ4Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
public class TemplateTransactionServiceImpl implements TemplateTransactionService {

    private final Quorum quorum;

    private final Credentials credentials;

    /**
     * @see TemplateTransactionService#loadTemplate(String) 
     */
    @Override
    public TemplateContract loadTemplate(final String contractAddress) {
        return TemplateContract.load(
                contractAddress,
                this.quorum,
                new ClientTransactionManager(this.quorum, this.credentials.getAddress(), null, new ArrayList<>()),
                new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
        );
    }

    /**
     * @see TemplateTransactionService#getMainContentOfTemplate(TemplateContract)
     */
    @Override
    public String getMainContentOfTemplate(final TemplateContract templateContract) {
        final String address = templateContract.getContractAddress();
        log.info("[BLOCKCHAIN] download main content of template from smart-contract with {} address", address);
        try {
            final String content = readMainContentFromSmartContract(templateContract);
            log.info("[BLOCKCHAIN] main content of template with {} address has been downloaded", address);
            return content;
        }
        catch (TransactionException ex) {
            log.error("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
            throw new QuorumTransactionException("Exception while reading data from Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
        }
    }

    private String readMainContentFromSmartContract(final TemplateContract templateContract) throws Exception {
        final DynamicBytes bytes = templateContract.mainContent().send();
        String data;
        if (bytes == null) {
            final int parts = getSizeOfTemplateMainContent(templateContract);
            if (parts == 0) {
                return "";
            }
            final StringBuilder fullText = new StringBuilder();
            for (int index = 0; index < parts; index++) {
                final Utf8String string = templateContract.mainContentOld(new Uint256(index)).send();
                fullText.append(string.getValue());
            }
            data = fullText.toString();
        }
        else {
            final byte[] decompressedData = LZ4Utils.decompress(bytes.getValue());
            data = new String(decompressedData, StandardCharsets.UTF_8);
        }
        return data;
    }

    private int getSizeOfTemplateMainContent(final TemplateContract templateContract) throws Exception {
        return templateContract.getMainContentArrayLength().send().getValue().intValue();
    }

    /**
     * @see TemplateTransactionService#getHeaderContentOfTemplate(TemplateContract)
     */
    @Override
    public String getHeaderContentOfTemplate(final TemplateContract templateContract) {
        final String address = templateContract.getContractAddress();
        log.info("[BLOCKCHAIN] download header content of template from smart-contract with {} address", address);
        try {
            final String content = readHeaderContentFromSmartContract(templateContract);
            log.info("[BLOCKCHAIN] header content of template with {} ID has been downloaded", address);
            return content;
        }
        catch (TransactionException ex) {
            log.error("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
            throw new QuorumTransactionException("Exception while processing transaction on Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
        }
    }

    private String readHeaderContentFromSmartContract(final TemplateContract templateContract) throws Exception {
        final DynamicBytes bytes = templateContract.headerContent().send();
        String data;
        if (bytes == null) {
            final int parts = getSizeOfTemplateHeaderContent(templateContract);
            if (parts == 0) {
                return "";
            }
            final StringBuilder fullText = new StringBuilder();
            for (int index = 0; index < parts; index++) {
                final Utf8String string = templateContract.headerContentOld(new Uint256(index)).send();
                fullText.append(string.getValue());
            }
            data = fullText.toString();
        }
        else {
            final byte[] decompressedData = LZ4Utils.decompress(bytes.getValue());
            data = new String(decompressedData, StandardCharsets.UTF_8);
        }
        return data;
    }

    private int getSizeOfTemplateHeaderContent(final TemplateContract templateContract) throws Exception {
        return templateContract.getHeaderContentArrayLength().send().getValue().intValue();
    }

    /**
     * @see TemplateTransactionService#getFooterContentOfTemplate(TemplateContract)
     */
    @Override
    public String getFooterContentOfTemplate(final TemplateContract templateContract) {
        final String address = templateContract.getContractAddress();
        log.info("[BLOCKCHAIN] download footer content of template from smart-contract with {} address", address);
        try {
            final String content = readFooterContentFromSmartContract(templateContract);
            log.info("[BLOCKCHAIN] footer content of template with {} ID has been downloaded", address);
            return content;
        }
        catch (TransactionException ex) {
            log.error("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
            throw new QuorumTransactionException("Exception while reading data from Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
        }
    }

    private String readFooterContentFromSmartContract(final TemplateContract templateContract) throws Exception {
        final DynamicBytes bytes = templateContract.footerContent().send();
        String data;
        if (bytes == null) {
            final int parts = getSizeOfTemplateFooterContent(templateContract);
            if (parts == 0) {
                return "";
            }
            final StringBuilder fullText = new StringBuilder();
            for (int index = 0; index < parts; index++) {
                final Utf8String string = templateContract.footerContentOld(new Uint256(index)).send();
                fullText.append(string.getValue());
            }
            data = fullText.toString();
        }
        else {
            final byte[] decompressedData = LZ4Utils.decompress(bytes.getValue());
            data = new String(decompressedData, StandardCharsets.UTF_8);
        }
        return data;
    }

    private int getSizeOfTemplateFooterContent(final TemplateContract templateContract) throws Exception {
        return templateContract.getFooterContentArrayLength().send().getValue().intValue();
    }

    /**
     * @see TemplateTransactionService#getFileTypeOfTemplate(TemplateContract)
     */
    @Override
    public String getFileTypeOfTemplate(final TemplateContract templateContract) {
        final String address = templateContract.getContractAddress();
        log.info("[BLOCKCHAIN] get file type of template with {} address of smart-contract", address);
        try {
            final String fileType = templateContract.fileType().send().getValue();
            log.info("[BLOCKCHAIN] file type of template with {} address has been received", address);
            return fileType;
        }
        catch (TransactionException ex) {
            log.error("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
            throw new QuorumTransactionException("Exception while reading data from Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
        }
    }
}
