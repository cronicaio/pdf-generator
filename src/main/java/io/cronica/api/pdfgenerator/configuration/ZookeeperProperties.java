package io.cronica.api.pdfgenerator.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {

    private String namespace;

    private String connectionString;

    private Integer connectionTimeout;

    private Integer sessionTimeout;
}
