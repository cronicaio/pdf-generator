package io.cronica.api.pdfgenerator.configuration;

import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.impl.ChromeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ChromeDevToolsProtocolBeans {

    private final ChromeDevToolsProtocolProperties devToolsProperties;

    @Bean
    public ChromeService chromeService() {
        return new ChromeServiceImpl(devToolsProperties.getHost(), devToolsProperties.getPort());
    }

}
