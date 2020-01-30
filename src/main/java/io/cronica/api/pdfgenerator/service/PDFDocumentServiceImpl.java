package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.aws.AWSS3BucketAdapter;
import io.cronica.api.pdfgenerator.component.aws.Repeater;
import io.cronica.api.pdfgenerator.component.ca.CronicaCAAdapter;
import io.cronica.api.pdfgenerator.component.dto.DataJsonDTO;
import io.cronica.api.pdfgenerator.component.entity.Document;
import io.cronica.api.pdfgenerator.component.entity.DocumentStatus;
import io.cronica.api.pdfgenerator.component.observer.DocumentObserver;
import io.cronica.api.pdfgenerator.component.redis.RedisDAO;
import io.cronica.api.pdfgenerator.component.redis.RedisDocument;
import io.cronica.api.pdfgenerator.database.model.DocumentCertificate;
import io.cronica.api.pdfgenerator.database.repository.DocumentCertificateRepository;
import io.cronica.api.pdfgenerator.exception.DocumentNotFoundException;
import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import io.cronica.api.pdfgenerator.utils.ChaCha20Utils;
import io.cronica.api.pdfgenerator.utils.DocumentUtils;
import io.cronica.api.pdfgenerator.utils.FileUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Hash;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class PDFDocumentServiceImpl implements PDFDocumentService {

    private static final String PDF_DOCUMENT_TYPE = "pdf";
    private static final String PDF_IN_MEMORY_DOCUMENT_TYPE = "pdf_in_memory";
    private static final String PDF_EMPTY_DOCUMENT_TYPE = "pdf_empty";
    private static final int TIME_TO_SLEEP_MILLIS = 500;
    private static final int TRIALS = 120;

    private final Repeater repeater;

    private final RedisDAO redisDAO;

    private final CronicaCAAdapter cronicaCAAdapter;

    private final DocumentObserver documentObserver;

    private final AWSS3BucketAdapter awss3BucketAdapter;

    private final DocumentTransactionService documentTransactionService;

    private final DocumentCertificateRepository documentCertificateRepository;

    /**
     * @see PDFDocumentService#generatePDFDocument(String)
     */
    @Override
    public Document generatePDFDocument(final String uuid) {
        validateUUID(uuid);
        validateDocumentInRedis(uuid);

        final RedisDocument redisDocument = this.redisDAO.get(uuid);
        switch (redisDocument.getType()) {
            case PDF_DOCUMENT_TYPE:
                if ( !this.redisDAO.exists(redisDocument.getDocumentID()) ) {
                    this.documentObserver.putDocumentIDToObserve(redisDocument.getDocumentID());
                }
                final DocumentCertificate docCert = getDocumentCertificateByID(redisDocument.getDocumentID());
                if ( !docCert.getIsStructured() ) {
                    return downloadNonStructuredDocument(redisDocument);
                } else {
                    try {
                        return generateStructuredDocument(redisDocument);
                    } catch (Exception ex) {
                        log.error("[SERVICE] exception while generating PDF of structured document", ex);
                        return new Document();
                    }
                }
            case PDF_EMPTY_DOCUMENT_TYPE:
            case PDF_IN_MEMORY_DOCUMENT_TYPE:
                try {
                    return generateStructuredDocument(redisDocument);
                } catch (Exception ex) {
                    log.error("[SERVICE] exception while generating PDF of structured document", ex);
                    return new Document();
                }
        }
        log.info("[SERVICE] there is no PDF document with '{}' UUID", uuid);
        throw new DocumentNotFoundException("There is no PDF document with '" + uuid + "' UUID");
    }

    /**
     * @see PDFDocumentService#generateExampleDocument(String)
     */
    @Override
    public Document generateExampleDocument(final String templateAddress) {
        try {
            if ( !this.redisDAO.exists(templateAddress) ) {
                this.documentObserver.putDocumentIDToObserve(templateAddress);
            }
            return generateStructuredDocument(new RedisDocument(PDF_DOCUMENT_TYPE, templateAddress));
        } catch (Exception ex) {
            log.error("[SERVICE] exception while generating PDF of structured document", ex);
            return new Document();
        }
    }

    /**
     * @see PDFDocumentService#generatePreviewDocument(String, DataJsonDTO)
     */
    @Override
    public UUID generatePreviewDocument(final String templateAddress, final DataJsonDTO jsonData) {
        final UUID uid = UUID.randomUUID();
        final String data = DocumentUtils.convertDataJsonToString(jsonData);
        this.documentObserver.putDocumentIDToObserve(templateAddress, data);
        this.redisDAO.save(uid.toString(), new RedisDocument(PDF_IN_MEMORY_DOCUMENT_TYPE, templateAddress), Duration.ofMinutes(1));
        return uid;
    }

    /**
     * @see PDFDocumentService#generatePreviewTemplate(DataBuffer)
     */
    @Override
    public UUID generatePreviewTemplate(final DataBuffer dataBuffer) {
        final UUID uid = UUID.randomUUID();
        final String dataId = Hash.sha3(uid.toString());
        FileUtility.validateZipArchive(dataBuffer);
        final ByteBuffer byteBuffer = dataBuffer.readPosition(0).asByteBuffer();
        this.redisDAO.save(uid.toString(), new RedisDocument(PDF_EMPTY_DOCUMENT_TYPE, dataId), StructuredPDFGenerator.TIME_TO_LIVE_PREVIEW_CACHE);
        this.redisDAO.saveData(byteBuffer.array(), dataId, StructuredPDFGenerator.TIME_TO_LIVE_PREVIEW_CACHE);
        this.documentObserver.putDocumentIDToObserve(dataId);
        return uid;
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
        final String deployedHash = this.documentTransactionService.getHash(documentAddress);

        final byte[] buffer = downloadPDFDocumentFromS3(redisDocument.getDocumentID());
        final String calculatedHash = DocumentUtils.getSha256(buffer);

        if ( !deployedHash.equals(calculatedHash) ) {
            log.info("[SERVICE] hash is not valid; expected: '{}', actual: '{}'", deployedHash, calculatedHash);
            throw new DocumentNotFoundException("Document with '" + redisDocument.getDocumentID() + "' does not found");
        }
        final byte[] signedDocument = this.cronicaCAAdapter.signDocument(buffer);
        final String fileName = "DC-" + redisDocument.getDocumentID() + ".pdf";

        return Document.newInstance(fileName, signedDocument);
    }

    private byte[] downloadPDFDocumentFromS3(final String documentId) {
        final String fileKey = "docs/DC-" + documentId + ".pdf";
        if ( !this.repeater.apply(this.awss3BucketAdapter::fileExists, fileKey) ) {
            throw new DocumentNotFoundException("Document with given " + documentId + " ID is not found");
        }
        return this.repeater.apply(this.awss3BucketAdapter::downloadFile, fileKey);
    }

    private Document generateStructuredDocument(final RedisDocument redisDocument) {
        final String documentID = redisDocument.getDocumentID();

        waitForDocument(documentID);

        final byte[] cachedPDF = this.redisDAO.getDataByID(redisDocument.getDocumentID());
        final byte[] documentBytes = ChaCha20Utils.decrypt(cachedPDF);

        final String fileName = "DC-" + redisDocument.getDocumentID() + ".pdf";
        final byte[] signedDocument = this.cronicaCAAdapter.signDocument(documentBytes);

        return Document.newInstance(fileName, signedDocument);
    }

    private void waitForDocument(final String documentID) {
        final CountDownLatch count = new CountDownLatch(TRIALS);
        while (count.getCount() > 0) {
            final Optional<DocumentStatus> status = this.documentObserver.check(documentID);
            if (status.isPresent() && status.get() == DocumentStatus.GENERATED || status.isEmpty()) {
                break;
            }
            else {
                count.countDown();
                try {
                    TimeUnit.MILLISECONDS.sleep(TIME_TO_SLEEP_MILLIS);
                }
                catch (InterruptedException ex) {
                    log.error("[SERVICE] exception while trying to sleep", ex);
                }
            }
        }
    }

    /**
     * Create basic folders for PDF documents, templates and QR codes.
     */
    @PostConstruct
    public void initFolders() throws IOException {
        final File[] directories = new File[] {
                new File("temp/pdf/"),
                new File("temp/qr/"),
                new File("temp/template/"),
                new File("temp/html/")
        };

        for (File directory : directories) {
            FileUtils.forceMkdir(directory);
        }
    }
}
