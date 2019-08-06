package io.cronica.api.pdfgenerator.configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class Beans {

    private static final int AWS_MAX_CONNECTIONS = 150;
    private static final int AWS_CONNECTION_TIMEOUT = 20_000;
    private static final int AWS_MAX_ERROR_RETRY = 5;

    private final AWSProperties awsProperties;

    @Bean
    public AmazonS3 initAmazonS3() {
        final BasicAWSCredentials awsCred = new BasicAWSCredentials(
                this.awsProperties.getAccessKey(), this.awsProperties.getSecretKey());
        return AmazonS3ClientBuilder.standard()
                .withRegion(this.awsProperties.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(awsCred))
                .withClientConfiguration(
                        new ClientConfiguration()
                            .withMaxConnections(AWS_MAX_CONNECTIONS)
                            .withConnectionTimeout(AWS_CONNECTION_TIMEOUT)
                            .withMaxErrorRetry(AWS_MAX_ERROR_RETRY)
                )
                .build();
    }
}
