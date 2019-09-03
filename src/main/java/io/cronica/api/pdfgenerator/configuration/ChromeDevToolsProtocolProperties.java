package io.cronica.api.pdfgenerator.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("chrome-devtools")
@Data
public class ChromeDevToolsProtocolProperties {

    private String host;
    private Integer port;

}
