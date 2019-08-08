package io.cronica.api.pdfgenerator.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import io.cronica.api.pdfgenerator.web.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor
@Configuration
public class Router {

    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> composedRouter() {
        return route(GET("/"), this.requestHandler::healthCheck)
                .andRoute(GET("/v1/pdf/{uuid}"), this.requestHandler::generatePDF);
    }
}
