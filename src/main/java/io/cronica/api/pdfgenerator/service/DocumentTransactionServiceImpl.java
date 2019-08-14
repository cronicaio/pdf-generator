package io.cronica.api.pdfgenerator.service;

import static io.cronica.api.pdfgenerator.utils.Constants.GAS_LIMIT;
import static io.cronica.api.pdfgenerator.utils.Constants.GAS_PRICE;

import io.cronica.api.pdfgenerator.component.wrapper.NonStructuredDoc;
import io.cronica.api.pdfgenerator.component.wrapper.StructuredDoc;
import io.cronica.api.pdfgenerator.exception.DocumentNotFoundException;
import io.cronica.api.pdfgenerator.utils.DeflateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class DocumentTransactionServiceImpl implements DocumentTransactionService {

    private final Web3j web3j;

    private final Credentials credentials;

    @Autowired
    public DocumentTransactionServiceImpl(
            @Qualifier("initWeb3j") Web3j web3j,
            Credentials credentials
    ) {
        this.web3j = web3j;
        this.credentials = credentials;
    }

    /**
     * @see DocumentTransactionService#loadDocument(Class, String)
     */
    @Override
    public <T extends Contract> T loadDocument(final Class<T> contractType, final String documentAddress) {
        log.info("[BLOCKCHAIN] loading of public document with {} address", documentAddress);
        final Constructor constructor = generateConstructor(contractType);
        T documentContractWrapper = (T) generatePublicContract(constructor, documentAddress);
        log.info("[BLOCKCHAIN] public document with {} address has been loaded", documentAddress);
        return documentContractWrapper;
    }

    private <T extends Contract> Constructor generateConstructor(final Class<T> contractType) {
        try {
            return contractType
                    .getDeclaredConstructor(
                            String.class,
                            Web3j.class,
                            Credentials.class,
                            ContractGasProvider.class
                    );
        }
        catch (NoSuchMethodException ex) {
            throw new RuntimeException("Exception during constructor initialization", ex);
        }
    }

    private Object generatePublicContract(final Constructor constructor, final String documentAddress) {
        try {
            return constructor.newInstance(
                    documentAddress,
                    this.web3j,
                    this.credentials,
                    new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
            );
        }
        catch (IllegalAccessException
               | InstantiationException
               | InvocationTargetException ex) {
            throw new RuntimeException("Exception during object instantiation", ex);
        }
    }

    /**
     * @see DocumentTransactionService#getBankCode(String)
     */
    @Override
    public String getBankCode(final String documentAddress) {
        log.info("[BLOCKCHAIN] get bank code of document with '{}' address", documentAddress);
        String bankCode;
        try {
            final StructuredDoc structuredDoc = loadDocument(StructuredDoc.class, documentAddress);
            bankCode = structuredDoc.getData().send().getValue1().getValue();
        }
        catch (Exception ex) {
            try {
                final NonStructuredDoc nonStructuredDoc = loadDocument(NonStructuredDoc.class, documentAddress);
                bankCode = nonStructuredDoc.getData().send().getValue1().getValue();
            }
            catch (NullPointerException e) {
                log.info("[BLOCKCHAIN] document with '{}' address does not found", documentAddress);
                return null;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        log.info("[BLOCKCHAIN] bank code of document with '{}' address has been retrieved", documentAddress);
        return bankCode;
    }

    /**
     * @see DocumentTransactionService#getHash(String)
     */
    @Override
    public String getHash(final String documentAddress) {
        log.info("[BLOCKCHAIN] get hash of document with '{}' address", documentAddress);
        try {
            final NonStructuredDoc nsDoc = loadDocument(NonStructuredDoc.class, documentAddress);
            final Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Bytes32, Utf8String, Uint8> data = nsDoc.getData().send();
            final String hash = Numeric.toHexString(data.getValue6().getValue());
            log.info("[BLOCKCHAIN] hash of document with '{}' address has been retrieved", documentAddress);
            return hash;
        }
        catch (NullPointerException ex) {
            log.info("[BLOCKCHAIN] document with {} address does not found", documentAddress);
            throw new DocumentNotFoundException("Document with " + documentAddress + " address", ex);
        }
        catch (TransactionException ex) {
            log.info("[BLOCKCHAIN] exception while reading data from Quorum node");
            throw new DocumentNotFoundException("Exception while reading data from Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * @see DocumentTransactionService#getTemplateID(String)
     */
    @Override
    public String getTemplateID(final String documentAddress) {
        log.info("[BLOCKCHAIN] get template ID of document with '{}' address", documentAddress);
        try {
            final StructuredDoc structuredDoc = loadDocument(StructuredDoc.class, documentAddress);
            final Tuple8<Utf8String, Utf8String, Utf8String, Uint64, Uint64, Utf8String, Address, Uint8> data = structuredDoc.getData().send();
            final String templateID = data.getValue7().getValue();
            log.info("[BLOCKCHAIN] template ID of document with '{}' address has been retrieved", documentAddress);
            return templateID;
        }
        catch (NullPointerException ex) {
            log.info("[BLOCKCHAIN] document with {} address does not found", documentAddress);
            throw new DocumentNotFoundException("Document with " + documentAddress + " address", ex);
        }
        catch (TransactionException ex) {
            log.info("[BLOCKCHAIN] exception while reading data from Quorum node");
            throw new DocumentNotFoundException("Exception while reading data from Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * @see DocumentTransactionService#getStructuredData(String)
     */
    @Override
    public String getStructuredData(final String documentAddress) {
        log.info("[BLOCKCHAIN] get structured data of document with '{}' address", documentAddress);
        try {
            final StructuredDoc structuredDoc = loadDocument(StructuredDoc.class, documentAddress);
            final DynamicBytes bytes = structuredDoc.structuredData().send();
            String data;
            if (bytes == null) {
                final StringBuilder sb = new StringBuilder();
                final int length = structuredDoc.getSizeOfStructuredData().send().getValue().intValue();
                for (int i = 0; i < length; i++) {
                    sb.append(structuredDoc.structuredDataOld(new Uint256(i)).send().getValue());
                }
                data = sb.toString();
            }
            else {
                final byte[] decompressedData = DeflateUtils.decompress(bytes.getValue());
                data = new String(decompressedData, StandardCharsets.UTF_8);
            }
            log.info("[BLOCKCHAIN] structured data of document with '{}' address has been retrieved", documentAddress);
            return data;
        }
        catch (NullPointerException ex) {
            log.info("[BLOCKCHAIN] document with {} address does not found", documentAddress);
            throw new DocumentNotFoundException("Document with " + documentAddress + " address", ex);
        }
        catch (TransactionException ex) {
            log.info("[BLOCKCHAIN] exception while reading data from Quorum node");
            throw new DocumentNotFoundException("Exception while reading data from Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
