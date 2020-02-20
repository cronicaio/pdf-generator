package io.cronica.api.pdfgenerator.component.ca;

import io.cronica.api.pdfgenerator.component.metrics.MethodID;
import io.cronica.api.pdfgenerator.component.metrics.MetricsLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static io.cronica.api.pdfgenerator.utils.Constants.REQUEST_ID_HEADER;
import static io.cronica.api.pdfgenerator.utils.Constants.REQUEST_ID_PARAM;

@RequiredArgsConstructor
@Slf4j
@Component
public class CronicaCAAdapterImpl implements CronicaCAAdapter {

    private static final String SIGN_DOCUMENT_URI = "/sign";

    private static final String BODY_REQUEST_FILE_KEY = "file";

    private final MetricsLogger metricsLogger;

    @Value("${cronica.ca.endpoint}")
    private String cronicaCAEndpoint;

    /**
     * @see CronicaCAAdapter#signDocument(byte[])
     */
    @Override
    public byte[] signDocument(final byte[] documentBytes) {
        log.info("[ADAPTER] send request for signing document");
        final String url = this.cronicaCAEndpoint + SIGN_DOCUMENT_URI;

        final HttpHeaders httpHeaders = formHeaders();
        final MultiValueMap<String, Object> body = formBody(documentBytes);

        return sendRequest(url, body, httpHeaders);
    }

    private HttpHeaders formHeaders() {
        final String requestID = ThreadContext.get(REQUEST_ID_PARAM);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set(REQUEST_ID_HEADER, requestID);

        return headers;
    }

    private MultiValueMap<String, Object> formBody(final byte[] documentBytes) {
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(BODY_REQUEST_FILE_KEY, new ByteArrayResource(documentBytes));
        return body;
    }

    private byte[] sendRequest(final String url, final MultiValueMap<String, Object> body, final HttpHeaders httpHeaders) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        byte[] signedDocument = new byte[]{};
        try {
            final ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, requestEntity, byte[].class);
            stopWatch.stop();

            if (responseEntity.getBody() != null) {
                signedDocument = responseEntity.getBody();
                log.info("[ADAPTER] document successfully signed");
                this.metricsLogger.incrementCount(MethodID.SIGN_DOCUMENT_SUCCESSFUL_REQUESTS);
                this.metricsLogger.logExecutionTime(MethodID.SIGN_DOCUMENT_REQUEST_TIME, stopWatch.getTotalTimeMillis());
            } else {
                log.debug("[ADAPTER] empty body in response");
                this.metricsLogger.incrementCount(MethodID.SIGN_DOCUMENT_FAILED_REQUESTS);
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("[ADAPTER] exception on Cronica CA", ex);
            this.metricsLogger.incrementCount(MethodID.SIGN_DOCUMENT_FAILED_REQUESTS);
        } finally {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
        }

        return signedDocument;
    }
}
