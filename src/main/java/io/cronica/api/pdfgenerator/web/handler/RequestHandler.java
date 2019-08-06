package io.cronica.api.pdfgenerator.web.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface RequestHandler {

    /**
     * Generate PDF document and return response client.
     *
     * @param serverRequest
     *          - {@link ServerRequest} object with client's request data
     * @return {@link ServerResponse} object with response data wrapped by {@link Mono} object
     */
    Mono<ServerResponse> generatePDF(ServerRequest serverRequest);
}
