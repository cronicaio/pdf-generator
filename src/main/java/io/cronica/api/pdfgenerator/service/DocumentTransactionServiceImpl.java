package io.cronica.api.pdfgenerator.service;

import static io.cronica.api.pdfgenerator.utils.Constants.GAS_LIMIT;
import static io.cronica.api.pdfgenerator.utils.Constants.GAS_PRICE;

import io.cronica.api.pdfgenerator.component.entity.DocumentStatus;
import io.cronica.api.pdfgenerator.component.wrapper.NonStructuredDoc;
import io.cronica.api.pdfgenerator.component.wrapper.StructuredDoc;
import io.cronica.api.pdfgenerator.exception.DocumentNotFoundException;
import io.cronica.api.pdfgenerator.utils.LZ4Utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.Utils;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Optional<DocumentData> documentDataOptional = loadData(documentAddress);
            bankCode = documentDataOptional.orElseThrow(NullPointerException::new).getBankCode();
        }
        catch (NullPointerException e) {
            log.info("[BLOCKCHAIN] document with '{}' address does not found", documentAddress);
            return null;
        }
        catch (IndexOutOfBoundsException e) {
            log.error("[BLOCKCHAIN] failed to get Bank Code, (Broken contract?)", e);
            return null;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
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
            Optional<DocumentData> documentDataOptional = loadData(documentAddress);
            final String hash = documentDataOptional.orElseThrow(NullPointerException::new).getHash();
            if (hash == null) {
                throw new NullPointerException();
            }
            log.info("[BLOCKCHAIN] hash of document with '{}' address has been retrieved", documentAddress);

            return hash;
        }
        catch (NullPointerException ex) {
            log.info("[BLOCKCHAIN] document with {} address does not found", documentAddress);
            throw new DocumentNotFoundException("Document with " + documentAddress + " address", ex);
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
            Optional<DocumentData> documentDataOptional = loadData(documentAddress);
            final String templateID = documentDataOptional.orElseThrow(NullPointerException::new).getTemplateId();
            log.info("[BLOCKCHAIN] template ID of document with '{}' address has been retrieved", documentAddress);
            return templateID;
        }
        catch (NullPointerException ex) {
            log.info("[BLOCKCHAIN] document with {} address does not found", documentAddress);
            throw new DocumentNotFoundException("Document with " + documentAddress + " address", ex);
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
                final byte[] decompressedData = LZ4Utils.decompress(bytes.getValue());
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

    private Optional<DocumentData> loadData(final String contractAddress) throws IOException {
        final Function function = new Function(StructuredDoc.FUNC_GETDATA, Collections.emptyList(), Collections.emptyList());
        EthCall ethCall = web3j.ethCall(Transaction.createEthCallTransaction(
                this.credentials.getAddress(), contractAddress, FunctionEncoder.encode(function)), DefaultBlockParameterName.LATEST)
                .send();

        String data = ethCall.getValue();
        List<List<TypeReference<?>>> rValuesList = Arrays.asList(StructuredDoc.R_VALUES_DATA_V1, StructuredDoc.R_VALUES_DATA_LEGACY, NonStructuredDoc.R_VALUES_DATA_V1, NonStructuredDoc.R_VALUES_DATA_LEGACY);
        for (List<TypeReference<?>> rValues : rValuesList) {
            log.info("[BLOCKCHAIN] trying decoding document data with types ({})",
                    Utils.convert(rValues).stream().map(TypeReference::getType).map(java.lang.reflect.Type::getTypeName).collect(Collectors.joining(",")));
            final List<Type> result = FunctionReturnDecoder.decode(data, Utils.convert(rValues));
            if (!result.isEmpty()) {
                final DocumentData documentData = new DocumentData();
                documentData.setBankCode(((Utf8String) result.get(0)).getValue().replaceAll("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]", ""));
                documentData.setDocumentName(((Utf8String) result.get(1)).getValue());
                documentData.setRecipientId(result.get(2).getTypeAsString().equals(Utf8String.TYPE_NAME) ? ((Utf8String) result.get(2)).getValue() : Numeric.toHexString(((Bytes20) result.get(2)).getValue()));
                documentData.setIssueTimestamp(((Uint64) result.get(3)).getValue().longValue());
                documentData.setExpireTimestamp(((Uint64) result.get(4)).getValue().longValue());
                if (result.get(5).getTypeAsString().equals(Utf8String.TYPE_NAME)) {
                    documentData.setRecipientName(((Utf8String) result.get(5)).getValue());
                    documentData.setTemplateId(((Address) result.get(6)).getValue());
                } else {
                    documentData.setHash(Numeric.toHexString(((Bytes32) result.get(5)).getValue()));
                    documentData.setRecipientName(((Utf8String) result.get(6)).getValue());
                }
                return Optional.of(documentData);
            }
        }
        log.warn("[BLOCKCHAIN] not any correct parameters signature found");
        return Optional.empty();
    }

    @NoArgsConstructor
    @Data
    private static class DocumentData {
        private String bankCode;
        private String documentName;
        private String recipientId;
        private Long issueTimestamp;
        private Long expireTimestamp;
        private String recipientName;
        private String templateId;
        private String hash;

    }
}
