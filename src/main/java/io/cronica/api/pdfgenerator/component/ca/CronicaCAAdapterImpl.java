package io.cronica.api.pdfgenerator.component.ca;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class CronicaCAAdapterImpl implements CronicaCAAdapter {

    private static final String SIGN_DOCUMENT_URI = "/sign";

    private static final String BODY_REQUEST_FILE_KEY = "file";

    @Value("${cronica.ca.endpoint}")
    private String cronicaCAEndpoint;

    /**
     * @see CronicaCAAdapter#signDocument(InputStream)
     */
    @Override
    public byte[] signDocument(final InputStream documentInputStream) {
        log.info("[ADAPTER] send request for signing document");
        final String url = this.cronicaCAEndpoint + SIGN_DOCUMENT_URI;

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            final byte[] bytesArr = IOUtils.toByteArray(documentInputStream);
            body.add(BODY_REQUEST_FILE_KEY, new ByteArrayResource(bytesArr));
        } catch (IOException ex) {
            log.error("[ADAPTER] exception while writing bytes to request body", ex);
            throw new RuntimeException("Exception while writing bytes to request body");
        }

        final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, requestEntity, byte[].class);
        final byte[] signedDocument = responseEntity.getBody();
        if (signedDocument == null) {
            log.info("[ADAPTER] returned empty response");
            return null;
        }

        log.info("[ADAPTER] document successfully signed");
        return signedDocument;
    }
}
