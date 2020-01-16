package io.cronica.api.pdfgenerator.web.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface RequestHandler {

    /**
     * @return {@link ServerResponse} object with {@code 200 OK} HTTP status
     */
    Mono<ServerResponse> healthCheck(ServerRequest serverRequest);

    /**
     * Generate PDF document and return response client.
     *
     * @param serverRequest
     *          - {@link ServerRequest} object with request data
     * @return {@link ServerResponse} object with response data wrapped by {@link Mono} object
     */
    Mono<ServerResponse> generatePDF(ServerRequest serverRequest);

    /**
     * Generate PDF document with default data and return response client as thumbnail pdf.
     *
     * @param serverRequest
     *          - {@link ServerRequest} object with request data
     * @return {@link ServerResponse} object with response data wrapped by {@link Mono} object
     */
    Mono<ServerResponse> generateThumbnail(ServerRequest serverRequest);

    /**
     * Generate PDF document with custom data and return response client as preview pdf.
     *
     * @param serverRequest
     *          - {@link ServerRequest} object with request data
     * @return {@link ServerResponse} object with response data wrapped by {@link Mono} object
     */
    Mono<ServerResponse> generateDocumentPreview(ServerRequest serverRequest);

    /**
     * Generate PDF document with default data using given template zip file and return response client as preview pdf.
     *
     * @param serverRequest
     *          - {@link ServerRequest} object with request data
     * @return {@link ServerResponse} object with response data wrapped by {@link Mono} object
     */
    Mono<ServerResponse> generatePreview(ServerRequest serverRequest);
}
