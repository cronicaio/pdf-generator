package io.cronica.api.pdfgenerator.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ZookeeperBeans {

    private final ZookeeperProperties properties;

    @Bean
    public CuratorFramework initCuratorFramework() {
        final CuratorFramework framework = CuratorFrameworkFactory
                .builder()
                .connectString(this.properties.getConnectionString())
                .sessionTimeoutMs(this.properties.getSessionTimeout())
                .connectionTimeoutMs(this.properties.getConnectionTimeout())
                .namespace(this.properties.getNamespace())
                .retryPolicy(new ExponentialBackoffRetry(1000, 5))
                .build();
        framework.start();

        return framework;
    }
}
