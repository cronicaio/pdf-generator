package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.entity.Document;
import io.cronica.api.pdfgenerator.component.redis.RedisDAO;
import io.cronica.api.pdfgenerator.component.redis.RedisDocument;
import io.cronica.api.pdfgenerator.database.model.DocumentCertificate;
import io.cronica.api.pdfgenerator.database.repository.DocumentCertificateRepository;
import io.cronica.api.pdfgenerator.exception.DocumentNotFoundException;
import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PDFDocumentServiceImpl implements PDFDocumentService {

    private static final String PDF_DOCUMENT_TYPE = "pdf";

    private final RedisDAO redisDAO;

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

        final DocumentCertificate dc = getDocumentCertificateByID(redisDocument.getDocumentID());
        final String fileName = "DC-" + redisDocument.getDocumentID() + ".pdf";

        return null;
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
}
