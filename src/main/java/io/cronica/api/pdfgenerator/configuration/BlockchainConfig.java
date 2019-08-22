package io.cronica.api.pdfgenerator.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "blockchain")
public class BlockchainConfig {

    private String walletPrivateKey;

    private String quorumNodeEndpoint;

    private String quorumBastionToken;

    private String issuersRegistryContractAddress;
}
