package io.cronica.api.pdfgenerator.service;

import static io.cronica.api.pdfgenerator.utils.Constants.GAS_LIMIT;
import static io.cronica.api.pdfgenerator.utils.Constants.GAS_PRICE;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cronica.api.pdfgenerator.component.dto.ItemDTO;
import io.cronica.api.pdfgenerator.component.entity.Template;
import io.cronica.api.pdfgenerator.component.wrapper.TemplateContract;
import io.cronica.api.pdfgenerator.component.wrapper.TemplateRegistry;
import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import io.cronica.api.pdfgenerator.exception.QuorumTransactionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
public class TemplateTransactionServiceImpl implements TemplateTransactionService {

    private final Quorum quorum;

    private final Credentials credentials;

    private final TemplateRegistry templateRegistry;

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
     * @see TemplateTransactionService#getTemplateAddressFromRegistry(Integer)
     */
    @Override
    public String getTemplateAddressFromRegistry(final Integer id) {
        log.info("[BLOCKCHAIN] get information from TemplateRegistry about template with {} ID", id);
        try {
            final Tuple2<Utf8String, Utf8String> templateData = getTemplateById(id);
            final String templateAddress = templateData.getValue1().getValue();
            log.info("[BLOCKCHAIN] information about template with {} ID has been received from TemplateRegistry", id);
            return templateAddress;
        }
        catch (NullPointerException | IndexOutOfBoundsException ex) {
            log.info("[BLOCKCHAIN] template with {} ID does not found in registry", id);
            throw new InvalidRequestException("Template with " + id + " ID does not found in registry");
        }
        catch (TransactionException ex) {
            log.error("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
            throw new QuorumTransactionException("Exception while reading data from Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
        }
    }

    /**
     * @see TemplateTransactionService#getTemplate(Integer)
     */
    @Override
    public Template getTemplate(final Integer index) throws InvalidRequestException {
        log.info("[BLOCKCHAIN] get data about template with {} ID in TemplateRegistry", index);
        try {
            final Template template = generateTemplate(index);
            log.info("[BLOCKCHAIN] info about template with {} ID in TemplateRegistry has been received", index);
            return template;
        }
        catch (NullPointerException | IndexOutOfBoundsException ex) {
            log.info("[BLOCKCHAIN] template with index {} does not found", index);
            throw new InvalidRequestException("Template with sent index is not found");
        }
        catch (TransactionException ex) {
            log.error("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
            throw new QuorumTransactionException("Exception while reading data from Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException("[BLOCKCHAIN] exception while reading data from Quorum node", ex);
        }
    }

    private Template generateTemplate(final Integer index) throws Exception {
        final Template template = new Template();

        final Tuple2<Utf8String, Utf8String> templateData = getTemplateById(index);
        final String contractAddress = templateData.getValue1().getValue();
        final TemplateContract templateContract = loadTemplate(contractAddress);

        final String documentName = getNameOfDocument(templateContract);
        final String items = getItemsOfTemplate(templateContract);

        template.setTemplateID(index);
        template.setName(documentName);
        template.setItems(convertJsonStringToItems(items));

        return template;
    }

    private Tuple2<Utf8String, Utf8String> getTemplateById(int id) throws Exception {
        log.info("[BLOCKCHAIN] get information from TemplateRegistry about template with {} ID", id);
        final Tuple2<Utf8String, Utf8String> info = this.templateRegistry.getTemplate(new Uint256(id)).send();
        log.info("[BLOCKCHAIN] information about template with {} ID has been received from TemplateRegistry", id);
        return info;
    }

    private String getItemsOfTemplate(TemplateContract templateContract) throws Exception {
        final String address = templateContract.getContractAddress();
        log.info("[BLOCKCHAIN] get items of template with {} address of smart-contract", address);
        final String items = templateContract.items().send().getValue();
        log.info("[BLOCKCHAIN] items of template with {} address of smart-contract has been received", address);
        return items;
    }

    private String getNameOfDocument(TemplateContract templateContract) throws Exception {
        final String address = templateContract.getContractAddress();
        log.info("[BLOCKCHAIN] get name of template with {} address of smart-contract", address);
        final String templateName = templateContract.name().send().getValue();
        log.info("[BLOCKCHAIN] name of template with {} address of smart-contract has been received", address);
        return templateName;
    }

    private ItemDTO convertJsonStringToItems(String jsonString) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        return mapper.readValue(jsonString, ItemDTO.class);
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
        final int parts = getSizeOfTemplateMainContent(templateContract);
        if (parts == 0) {
            return "";
        }
        final StringBuilder fullText = new StringBuilder();
        for (int index = 0; index < parts; index++) {
            final Utf8String string = templateContract.mainContent(new Uint256(index)).send();
            fullText.append(string.getValue());
        }
        return fullText.toString();
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
        final int parts = getSizeOfTemplateHeaderContent(templateContract);
        if (parts == 0) {
            return "";
        }
        final StringBuilder fullText = new StringBuilder();
        for (int index = 0; index < parts; index++) {
            final Utf8String string = templateContract.headerContent(new Uint256(index)).send();
            fullText.append(string.getValue());
        }
        return fullText.toString();
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
        final int parts = getSizeOfTemplateFooterContent(templateContract);
        if (parts == 0) {
            return "";
        }
        final StringBuilder fullText = new StringBuilder();
        for (int index = 0; index < parts; index++) {
            final Utf8String string = templateContract.footerContent(new Uint256(index)).send();
            fullText.append(string.getValue());
        }
        return fullText.toString();
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
