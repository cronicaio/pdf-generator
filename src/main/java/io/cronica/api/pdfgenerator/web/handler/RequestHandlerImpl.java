package io.cronica.api.pdfgenerator.web.handler;

import io.cronica.api.pdfgenerator.component.dto.APIErrorResponseDTO;
import io.cronica.api.pdfgenerator.component.entity.Document;
import io.cronica.api.pdfgenerator.exception.*;
import io.cronica.api.pdfgenerator.service.PDFDocumentService;
import io.cronica.api.pdfgenerator.utils.PDFUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.web3j.utils.Numeric;
import reactor.core.publisher.Mono;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestHandlerImpl implements RequestHandler {

    private static final String UUID_PATH_VARIABLE = "uuid";
    private static final String TEMPLATE_ID_VARIABLE = "templateId";
    private static final String TEMPLATE_FILE = "file";

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
                .map(this.pdfDocumentService::generatePDFDocument)
                .flatMap(this::generatePDFResponse)
                .onErrorResume(this::handleError);
    }

    /**
     * @see RequestHandler#generateThumbnail(ServerRequest)
     */
    @Override
    public Mono<ServerResponse> generateThumbnail(final ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.pathVariable(TEMPLATE_ID_VARIABLE))
                .map(Base64.getUrlDecoder()::decode)
                .map(Numeric::toHexString)
                .map(this.pdfDocumentService::generateExampleDocument)
                .map(this::convertPDFDocumentToPNG)
                .flatMap(this::generatePNGResponse)
                .onErrorResume(this::handleError);
    }

    /**
     * @see RequestHandler#generateDocumentPreview(ServerRequest)
     */
    @Override
    public Mono<ServerResponse> generateDocumentPreview(final ServerRequest serverRequest) {
        final String templateId = serverRequest.pathVariable(TEMPLATE_ID_VARIABLE);
        final String templateAddress = Numeric.toHexString(Base64.getUrlDecoder().decode(templateId));
        return serverRequest.bodyToMono(String.class)
                .map(body -> this.pdfDocumentService.generatePreviewDocument(templateAddress, body))
                .map(uid -> serverRequest.uriBuilder().replacePath("/v1/pdf/" + uid.toString()).build())
                .map(this::toLinkMap)
                .flatMap(this::generateJSONResponse)
                .onErrorResume(this::handleError);
    }

    @Override
    public Mono<ServerResponse> generatePreview(ServerRequest serverRequest) {
        return serverRequest.body(BodyExtractors.toMultipartData())
                .map(MultiValueMap::toSingleValueMap)
                .map(stringPartMap -> stringPartMap.get(TEMPLATE_FILE))
                .flatMap(m -> DataBufferUtils.join(m.content()))
                .map(this.pdfDocumentService::generatePreviewTemplate)
                .map(uid -> serverRequest.uriBuilder().replacePath("/v1/pdf/" + uid.toString()).build())
                .map(this::toLinkMap)
                .flatMap(this::generateJSONResponse)
                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> generatePDFResponse(final Document document) {
        return generateFileResponse(document, MediaType.APPLICATION_PDF);
    }


    private Mono<ServerResponse> generatePNGResponse(final Document document) {
        return generateFileResponse(document, MediaType.IMAGE_PNG);
    }

    private Mono<ServerResponse> generateFileResponse(final Document document, final MediaType mediaType) {
        final Resource resource = new ByteArrayResource(document.getFile());
        return ServerResponse
                .ok()
                .contentType(mediaType)
                .header("Content-Disposition", "attachment; filename=" + document.getFileName())
                .body(BodyInserters.fromResource(resource));
    }

    private Mono<ServerResponse> generateJSONResponse(final Map<String, URI> body) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(body);
    }

    private Document convertPDFDocumentToPNG(final Document document) {
        BufferedImage bufferedImage = PDFUtils.convertPdfToImage(document.getFile());
        BufferedImage scaledImage = PDFUtils.resize(bufferedImage, 420, 600);
        return Document.newInstance(document.getFileName().replaceAll("pdf", "png"), PDFUtils.asPng(scaledImage).toByteArray());
    }

    private Map<String, URI> toLinkMap(final URI uri) {
        return Collections.singletonMap("link", uri);
    }

    private Mono<ServerResponse> handleError(final Throwable throwable) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (throwable instanceof CronicaRuntimeException) {
            if (throwable instanceof InvalidRequestException) {
                errorCode = ErrorCode.INVALID_REQUEST;
                httpStatus = HttpStatus.BAD_REQUEST;
            }
            else if (throwable instanceof DocumentNotFoundException) {
                errorCode = ErrorCode.DOCUMENT_NOT_FOUND;
                httpStatus = HttpStatus.NOT_FOUND;
            }
            else if (throwable instanceof IssuerNotFoundException) {
                errorCode = ErrorCode.ISSUER_NOT_FOUND;
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            }
            else if (throwable instanceof QuorumTransactionException) {
                errorCode = ErrorCode.QUORUM_NODE_EXCEPTION;
                httpStatus = HttpStatus.BAD_GATEWAY;
            }
        }

        return ServerResponse
                .status(httpStatus)
                .body(Mono.just(new APIErrorResponseDTO(errorCode)), APIErrorResponseDTO.class);
    }
}
