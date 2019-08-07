package io.cronica.api.pdfgenerator.web.handler;

import io.cronica.api.pdfgenerator.component.entity.Document;
import io.cronica.api.pdfgenerator.service.PDFDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestHandlerImpl implements RequestHandler {

    private static final String UUID_PATH_VARIABLE = "uuid";

    private final PDFDocumentService pdfDocumentService;

    /**
     * @see RequestHandler#healthCheck(ServerRequest)
     */
    @Override
    public Mono<ServerResponse> healthCheck(final ServerRequest serverRequest) {
        return ServerResponse.ok().build();
    }

    /**
     * @see RequestHandler#generatePDF(ServerRequest)
     */
    @Override
    public Mono<ServerResponse> generatePDF(final ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.pathVariable(UUID_PATH_VARIABLE))
                .map(pdfDocumentService::generatePDFDocument)
                .flatMap(this::generateResponse);
    }

    private Mono<ServerResponse> generateResponse(final Document document) {
        final Resource resource = new ByteArrayResource(document.getFile());
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "attachment; filename=" + document.getFileName())
                .body(BodyInserters.fromResource(resource));
    }
}
