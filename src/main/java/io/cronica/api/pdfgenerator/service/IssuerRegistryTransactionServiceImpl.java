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
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tuples.generated.Tuple5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssuerRegistryTransactionServiceImpl implements IssuerRegistryTransactionService {

    private final IssuersRegistry issuersRegistry;

    @Value("${issuer.bank-code}")
    private String bankCode;

    /**
     * @see IssuerRegistryTransactionService#getIssuers()
     */
    @Override
    public List<Issuer> getIssuers() {
        log.info("[BLOCKCHAIN] get all issuers from registry");
        try {
            final List<Issuer> issuers = new ArrayList<>();
            final long numberOfIssuers = getCountOfIssuers();
            log.info("[BLOCKCHAIN] {} issuers are found", numberOfIssuers);
            for (long i = 0; i < numberOfIssuers; i++) {
                final Issuer issuer = getIssuerByBankCode(getBankCodeById(i));
                issuers.add(issuer);
            }
            log.info("[BLOCKCHAIN] {} issuers have been retrieved", numberOfIssuers);

            // In order to prevent errors, user does not receive the public key of the node that
            // is being used
            return issuers.stream()
                    .filter(issuer -> !issuer.getBankCode().equals(this.bankCode))
                    .collect(Collectors.toList());
        }
        catch (TransactionException ex) {
            log.error("[BLOCKCHAIN] exception while processing transaction on Quorum node", ex);
            throw new QuorumTransactionException("Exception while processing transaction on Quorum node");
        }
        catch (Exception ex) {
            throw new RuntimeException("[BLOCKCHAIN] exception while getting issuer with " + this.bankCode + " bank code", ex);
        }
    }

    private String getBankCodeById(final Long id) throws Exception {
        log.info("[BLOCKCHAIN] get code of the bank with {} ID in IssuersRegistry", id);
        final Utf8String bankCode = this.issuersRegistry.getBankCodeById(new Uint64(id)).send();
        log.info("[BLOCKCHAIN] received code of the bank with {} ID in IssuersRegistry", id);
        return bankCode.getValue();
    }

    private long getCountOfIssuers() throws Exception {
        final Uint256 numberOfIssuers = this.issuersRegistry.getNumberOfIssuers().send();
        return numberOfIssuers.getValue().longValue();
    }

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
