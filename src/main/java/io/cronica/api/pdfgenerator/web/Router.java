package io.cronica.api.pdfgenerator.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import io.cronica.api.pdfgenerator.web.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class Router {

    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> composedRouter() {
        return route(GET("/pdf/{uuid}"), this.requestHandler::generatePDF);
    }
}
