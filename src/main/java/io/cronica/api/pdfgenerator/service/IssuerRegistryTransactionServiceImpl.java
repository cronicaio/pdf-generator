package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.entity.Issuer;
import io.cronica.api.pdfgenerator.component.wrapper.IssuersRegistry;
import io.cronica.api.pdfgenerator.exception.IssuerNotFoundException;
import io.cronica.api.pdfgenerator.exception.QuorumTransactionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tuples.generated.Tuple5;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssuerRegistryTransactionServiceImpl implements IssuerRegistryTransactionService {

    private final IssuersRegistry issuersRegistry;

    @Value("${issuer.bank-code}")
    private String bankCode;

    /**
     * @see IssuerRegistryTransactionService#getIssuerByBankCode(String)
     */
    @Override
    public Issuer getIssuerByBankCode(final String bankCode) {
        log.info("[BLOCKCHAIN] get issuer with {} bank code", bankCode);
        try {
            final Tuple5<Utf8String, Utf8String, Utf8String, Utf8String, Utf8String> issuerTuple =
                    this.issuersRegistry.getIssuer(new Utf8String(bankCode)).send();
            log.info("[BLOCKCHAIN] found issuer with {} bank code", bankCode);
            return Issuer.newInstance(bankCode, issuerTuple);
        }
        catch (NullPointerException ex) {
            log.info("[BLOCKCHAIN] issuer with {} bank code does not found", bankCode);
            throw new IssuerNotFoundException("Issuer with " + bankCode + " code does not found");
        }
        catch (TransactionException ex) {
            log.error("[BLOCKCHAIN] exception while processing transaction on Quorum node", ex);
            throw new QuorumTransactionException("Exception while processing transaction on Quorum node");
        }
        catch (Exception ex) {
            log.error("[BLOCKCHAIN] exception while getting issuer with {} bank code", bankCode, ex);
            throw new RuntimeException(ex);
        }
    }
}
