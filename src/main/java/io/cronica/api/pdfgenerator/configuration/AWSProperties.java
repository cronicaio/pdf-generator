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
@ConfigurationProperties(prefix = "aws")
public class AWSProperties {

    private String region;

    private String accessKey;

    private String secretKey;

    private String s3BucketName;
}
