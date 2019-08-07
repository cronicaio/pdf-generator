package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.aws.AWSS3BucketAdapter;
import io.cronica.api.pdfgenerator.component.aws.Repeater;
import io.cronica.api.pdfgenerator.component.ca.CronicaCAAdapter;
import io.cronica.api.pdfgenerator.component.entity.Document;
import io.cronica.api.pdfgenerator.component.redis.RedisDAO;
import io.cronica.api.pdfgenerator.component.redis.RedisDocument;
import io.cronica.api.pdfgenerator.database.model.DocumentCertificate;
import io.cronica.api.pdfgenerator.database.repository.DocumentCertificateRepository;
import io.cronica.api.pdfgenerator.exception.DocumentNotFoundException;
import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import io.cronica.api.pdfgenerator.utils.DocumentUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class PDFDocumentServiceImpl implements PDFDocumentService {

    private static final String PDF_DOCUMENT_TYPE = "pdf";

    private final Repeater repeater;

    private final RedisDAO redisDAO;

    private final CronicaCAAdapter cronicaCAAdapter;

    private final AWSS3BucketAdapter awss3BucketAdapter;

    private final DocumentTransaction documentTransaction;

    private final DocumentCertificateRepository documentCertificateRepository;

    /**
     * @see PDFDocumentService#generatePDFDocument(String)
     */
    @Override
    public Document generatePDFDocument(final String uuid) {
        validateUUID(uuid);
        validateDocumentInRedis(uuid);

        final RedisDocument redisDocument = this.redisDAO.get(uuid);
        if ( !redisDocument.getType().equals(PDF_DOCUMENT_TYPE) ) {
            log.info("[SERVICE] there is no PDF document with '{}' UUID", uuid);
            throw new DocumentNotFoundException("There is no PDF document with '" + uuid + "' UUID");
        }

        final DocumentCertificate docCert = getDocumentCertificateByID(redisDocument.getDocumentID());
        if ( !docCert.getIsStructured() ) {
            return downloadNonStructuredDocument(redisDocument);
        }
        else {
            // TODO: implement for StructuredDocument
            return null;
        }
    }

    private void validateUUID(final String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            log.info("[SERVICE] UUID is empty");
            throw new InvalidRequestException("UUID could not be null");
        }
    }

    private void validateDocumentInRedis(final String uuid) {
        if ( !this.redisDAO.exists(uuid) ) {
            log.info("[SERVICE] there is no record under '{}' UUID. Expired link.", uuid);
            throw new DocumentNotFoundException("Link to document is expired");
        }
    }

    private DocumentCertificate getDocumentCertificateByID(final String documentID) {
        log.info("[DATABASE] searching document with '{}' ID", documentID);
        final DocumentCertificate dc = this.documentCertificateRepository.findByDocumentID(documentID);
        if (dc == null) {
            log.info("[DATABASE] document with '{}' ID does not found", documentID);
            throw new DocumentNotFoundException("Document with '" + documentID + "'document ID is not found");
        }
        log.info("[DATABASE] document with '{}' ID has been found", dc.toString());
        return dc;
    }

    private Document downloadNonStructuredDocument(final RedisDocument redisDocument) {
        final String documentAddress = DocumentUtils.readDocumentAddress(redisDocument.getDocumentID());
        final String deployedHash = this.documentTransaction.getHash(documentAddress);

        final byte[] buffer = downloadPDFDocumentFromS3(redisDocument.getDocumentID());
        final String calculatedHash = DocumentUtils.getSha256(buffer);

        if (!deployedHash.equals(calculatedHash)) {
            log.info("[SERVICE] hash is not valid; expected: '{}', actual: '{}'", deployedHash, calculatedHash);
            throw new DocumentNotFoundException("Document with '" + redisDocument.getDocumentID() + "' does not found");
        }
        final InputStream signedDoc = this.cronicaCAAdapter.signDocument(new ByteArrayInputStream(buffer));
        final String fileName = "DC-" + redisDocument.getDocumentID() + ".pdf";

        return Document.newInstance(fileName, signedDoc);
    }

    private byte[] downloadPDFDocumentFromS3(final String documentId) {
        final String fileKey = "docs/DC-" + documentId + ".pdf";
        if ( !this.repeater.apply(this.awss3BucketAdapter::fileExists, fileKey) ) {
            throw new DocumentNotFoundException("Document with given " + documentId + " ID is not found");
        }
        return this.repeater.apply(this.awss3BucketAdapter::downloadFile, fileKey);
    }

}
