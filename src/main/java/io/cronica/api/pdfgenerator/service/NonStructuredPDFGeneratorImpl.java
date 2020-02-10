package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.aws.AWSS3BucketAdapter;
import io.cronica.api.pdfgenerator.component.aws.Repeater;
import io.cronica.api.pdfgenerator.component.kafka.entities.GeneratePdfRequest;
import io.cronica.api.pdfgenerator.component.redis.RedisDAO;
import io.cronica.api.pdfgenerator.exception.DocumentNotFoundException;
import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import io.cronica.api.pdfgenerator.utils.ChaCha20Utils;
import io.cronica.api.pdfgenerator.utils.DocumentUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service("nonStructuredPDFGenerator")
public class NonStructuredPDFGeneratorImpl implements PDFGenerator {

    private final Repeater repeater;
    private final AWSS3BucketAdapter awss3BucketAdapter;
    private final DocumentTransactionService documentTransactionService;
    private final RedisDAO redisDAO;

    /**
     * @see PDFGenerator#generateAndSave(GeneratePdfRequest)
     */
    @Override
    public void generateAndSave(final GeneratePdfRequest request) {
        byte[] documentData;
        final Duration expire;
        if (request.getDocument().getStorageType() == GeneratePdfRequest.StorageType.S3) {
            documentData = downloadNonStructuredDocument(request.getDocument().getId());
            expire = TIME_TO_LIVE;
        } else {
            throw new InvalidRequestException("Incorrect PDF generating request");
        }
        // encrypt PDF before caching to Redis
        final byte[] encryptedDocument = ChaCha20Utils.encrypt(documentData);
        this.redisDAO.saveData(encryptedDocument, request.getResultId().toString(), expire);
    }

    private byte[] downloadNonStructuredDocument(final String documentId) {
        final String documentAddress = DocumentUtils.readDocumentAddress(documentId);
        final String deployedHash = this.documentTransactionService.getHash(documentAddress);

        final byte[] buffer = downloadPDFDocumentFromS3(documentId);
        final String calculatedHash = DocumentUtils.getSha256(buffer);

        if ( !deployedHash.equals(calculatedHash) ) {
            log.info("[SERVICE] hash is not valid; expected: '{}', actual: '{}'", deployedHash, calculatedHash);
            throw new DocumentNotFoundException("Document with '" + documentId + "' does not found");
        }
        return buffer;
    }

    private byte[] downloadPDFDocumentFromS3(final String documentId) {
        final String fileKey = "docs/DC-" + documentId + ".pdf";
        if ( !this.repeater.apply(this.awss3BucketAdapter::fileExists, fileKey) ) {
            throw new DocumentNotFoundException("Document with given " + documentId + " ID is not found");
        }
        return this.repeater.apply(this.awss3BucketAdapter::downloadFile, fileKey);
    }

}
