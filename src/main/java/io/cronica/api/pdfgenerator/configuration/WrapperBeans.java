package io.cronica.api.pdfgenerator.configuration;

import static io.cronica.api.pdfgenerator.utils.Constants.GAS_LIMIT;
import static io.cronica.api.pdfgenerator.utils.Constants.GAS_PRICE;

import io.cronica.api.pdfgenerator.component.wrapper.IssuersRegistry;
import io.cronica.api.pdfgenerator.component.wrapper.TemplateRegistry;
import io.cronica.api.pdfgenerator.database.model.SmartContract;
import io.cronica.api.pdfgenerator.database.repository.SmartContractRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.util.ArrayList;

@Slf4j
@Configuration
public class WrapperBeans implements ApplicationListener<ApplicationReadyEvent> {

    private static final String TEMPLATE_REGISTRY_CONTRACT_NAME = "template_registry";

    private static String templateRegistryContractAddress;

    private final Web3j web3j;

    private final Quorum quorum;

    private final Credentials credentials;

    private final String issuersRegistryContractAddress;

    private final SmartContractRepository smartContractRepository;

    private final ClientTransactionManager clientTransactionManager;

    @Autowired
    public WrapperBeans(
            @Qualifier("initWeb3j") Web3j web3j,
            Quorum quorum,
            Credentials credentials,
            SmartContractRepository smartContractRepository,
            BlockchainConfig blockchainConfig
    ) {
        this.web3j = web3j;
        this.quorum = quorum;
        this.credentials = credentials;
        this.clientTransactionManager = new ClientTransactionManager(
                quorum, credentials.getAddress(), null, new ArrayList<>()
        );
        this.smartContractRepository = smartContractRepository;
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

    @Bean
    @Scope("prototype")
    public TemplateRegistry initTemplateRegistry() {
        return TemplateRegistry.load(
                templateRegistryContractAddress,
                this.quorum,
                this.clientTransactionManager,
                new StaticGasProvider(GAS_PRICE, GAS_LIMIT)
        );
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        if (event.getApplicationContext() instanceof AnnotationConfigServletWebServerApplicationContext) {
            try {
                if (StringUtils.isEmpty(templateRegistryContractAddress)) {
                    templateRegistryContractAddress = loadTemplateRegistry();
                }
            }
            catch (Exception ex) {
                log.error("Exception while deploying TemplateRegistry contract", ex);
            }
        }
    }

    private String loadTemplateRegistry() {
        final SmartContract scd = this.smartContractRepository.findByContractName(TEMPLATE_REGISTRY_CONTRACT_NAME);
        if (scd == null) {
            throw new NullPointerException();
        }
        else {
            return scd.getAddress();
        }
    }
}
