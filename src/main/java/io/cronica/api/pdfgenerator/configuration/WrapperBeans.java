package io.cronica.api.pdfgenerator.configuration;

import static io.cronica.api.pdfgenerator.utils.Constants.GAS_LIMIT;
import static io.cronica.api.pdfgenerator.utils.Constants.GAS_PRICE;

import io.cronica.api.pdfgenerator.component.wrapper.IssuersRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.StaticGasProvider;

@Slf4j
@Configuration
public class WrapperBeans {

    private final Web3j web3j;

    private final Credentials credentials;

    private final String issuersRegistryContractAddress;

    @Autowired
    public WrapperBeans(
            @Qualifier("initWeb3j") Web3j web3j,
            Credentials credentials,
            BlockchainConfig blockchainConfig
    ) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.issuersRegistryContractAddress = blockchainConfig.getIssuersRegistryContractAddress();
    }

    @Bean
    @Scope("prototype")
    public IssuersRegistry initIssuersRegistry() {
        return IssuersRegistry.load(
                this.issuersRegistryContractAddress,
                this.web3j,
                this.credentials,
                new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
        );
    }
}
