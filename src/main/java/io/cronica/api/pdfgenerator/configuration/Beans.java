package io.cronica.api.pdfgenerator.configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.KryoCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.utils.Async;

import java.math.BigInteger;

@RequiredArgsConstructor
@Configuration
public class Beans {

    private static final String BASTION_TOKEN_HEADER = "X-TOKEN";

    private static final int NODE_POLLING_INTERVAL = 100;

    private static final int AWS_MAX_CONNECTIONS = 150;
    private static final int AWS_CONNECTION_TIMEOUT = 20_000;
    private static final int AWS_MAX_ERROR_RETRY = 5;

    private final RedisConfig redisConfig;

    private final AWSProperties awsProperties;

    private final BlockchainConfig blockchainConfig;

    @Bean
    public Quorum initQuorumNode() {
        final HttpService httpService = new HttpService(this.blockchainConfig.getQuorumNodeEndpoint());
        httpService.addHeader(BASTION_TOKEN_HEADER, this.blockchainConfig.getQuorumBastionToken());
        return Quorum.build(httpService);
    }

    @Bean
    public Web3j initWeb3j() {
        final HttpService httpService = new HttpService(this.blockchainConfig.getQuorumNodeEndpoint());
        httpService.addHeader(BASTION_TOKEN_HEADER, this.blockchainConfig.getQuorumBastionToken());
        return Web3j.build(httpService, NODE_POLLING_INTERVAL, Async.defaultExecutorService());
    }

    @Bean
    public Credentials initCredentials() {
        return Credentials.create(ECKeyPair.create(BigInteger.ZERO));
    }

    @Bean
    public RedissonClient initRedissonClient() {
        final Config config = new Config();
        config.setCodec(new KryoCodec())
                .useSingleServer()
                .setAddress("redis://" + this.redisConfig.getHost() + ":" + this.redisConfig.getPort());

        return Redisson.create(config);
    }

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
