package io.cronica.api.pdfgenerator.component.ca;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class CronicaCAAdapterImpl implements CronicaCAAdapter {

    private static final String SIGN_DOCUMENT_URI = "/sign";

    private static final String BODY_REQUEST_FILE_KEY = "file";

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
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
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

        byte[] signedDocument = new byte[]{};
        try {
            final ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, requestEntity, byte[].class);
            if (responseEntity.getBody() != null) {
                signedDocument = responseEntity.getBody();
                log.info("[ADAPTER] document successfully signed");
            }
            else {
                log.debug("[ADAPTER] empty body in response");
            }
        }
        catch (HttpServerErrorException ex) {
            log.error("[ADAPTER] exception on Cronica CA", ex);
        }

        return signedDocument;
    }
}
