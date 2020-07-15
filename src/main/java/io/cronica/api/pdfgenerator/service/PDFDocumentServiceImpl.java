package io.cronica.api.pdfgenerator.service;

import com.amazonaws.util.Md5Utils;
import com.google.common.base.Preconditions;
import io.cronica.api.pdfgenerator.component.ca.CronicaCAAdapter;
import io.cronica.api.pdfgenerator.component.dto.DataJsonDTO;
import io.cronica.api.pdfgenerator.component.entity.Document;
import io.cronica.api.pdfgenerator.component.entity.DocumentStatus;
import io.cronica.api.pdfgenerator.component.kafka.entities.GeneratePdfRequest;
import io.cronica.api.pdfgenerator.component.observer.DocumentObserver;
import io.cronica.api.pdfgenerator.component.redis.RedisDAO;
import io.cronica.api.pdfgenerator.component.redis.RedisDocument;
import io.cronica.api.pdfgenerator.database.model.DocumentCertificate;
import io.cronica.api.pdfgenerator.database.repository.DocumentCertificateRepository;
import io.cronica.api.pdfgenerator.exception.DocumentNotFoundException;
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
import org.web3j.utils.Numeric;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class PDFDocumentServiceImpl implements PDFDocumentService {

    private static final int TIME_TO_SLEEP_MILLIS = 500;
    private static final int TRIALS = 120;

    private final RedisDAO redisDAO;

    private final CronicaCAAdapter cronicaCAAdapter;

    private final DocumentObserver documentObserver;

    private final DocumentCertificateRepository documentCertificateRepository;

    /**
     * @see PDFDocumentService#generatePDFDocument(String)
     */
    @Override
    public Document generatePDFDocument(final String uuid) {
        Preconditions.checkArgument(isValidUUID(uuid), "Invalid UUID string");
        validateDocumentInRedis(uuid);

        final Optional<RedisDocument> redisDocument = this.redisDAO.get(uuid);
        final UUID uid = redisDocument.map(document -> {
            final DocumentCertificate docCert = getDocumentCertificateByID(document.getDocumentID());
            final GeneratePdfRequest.StorageType type;
            if (docCert.getIsStructured()) type = GeneratePdfRequest.StorageType.BLOCKCHAIN;
            else type = GeneratePdfRequest.StorageType.S3;
            final UUID resultId = this.createResultId(document.getDocumentID());
            final Optional<DocumentStatus> status = this.documentObserver.check(resultId.toString());
            if ( (!this.redisDAO.exists(resultId.toString())) && status.isEmpty() ) {
                final GeneratePdfRequest request = GeneratePdfRequest.builder()
                        .resultId(resultId)
                        .template(new GeneratePdfRequest.Content(null, GeneratePdfRequest.StorageType.NOT_SET))
                        .document(new GeneratePdfRequest.Content(document.getDocumentID(), type))
                        .build();
                this.documentObserver.sendGeneratePdfRequest(request);
            }
            return resultId;
        }).orElse(UUID.fromString(uuid));
        try {
            return generateDownloadableDocument(uid);
        } catch (Exception ex) {
            log.info("[SERVICE] there is no PDF document with '{}' UUID", uuid);
            throw new DocumentNotFoundException("There is no PDF document with '" + uuid + "' UUID");
        }
    }

    /**
     * @see PDFDocumentService#generateExampleDocument(String)
     */
    @Override
    public Document generateExampleDocument(final String templateAddress) {
        try {
            final UUID uid = this.createResultId(templateAddress);
            if ( !this.redisDAO.exists(uid.toString()) ) {
                final GeneratePdfRequest request = GeneratePdfRequest.builder()
                        .resultId(uid)
                        .template(new GeneratePdfRequest.Content(templateAddress, GeneratePdfRequest.StorageType.BLOCKCHAIN))
                        .document(new GeneratePdfRequest.Content(null, GeneratePdfRequest.StorageType.NOT_SET))
                        .build();
                this.documentObserver.sendGeneratePdfRequest(request);
            }
            return generateDownloadableDocument(uid);
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
        final String dataId = Hash.sha3(uid.toString());
        final String data = DocumentUtils.convertDataJsonToString(jsonData);
        final GeneratePdfRequest request = GeneratePdfRequest.builder()
                .resultId(uid)
                .template(new GeneratePdfRequest.Content(templateAddress, GeneratePdfRequest.StorageType.BLOCKCHAIN))
                .document(new GeneratePdfRequest.Content(dataId, GeneratePdfRequest.StorageType.CACHE))
                .build();
        this.documentObserver.sendGeneratePdfRequest(request);
        this.redisDAO.saveData(data.getBytes(StandardCharsets.UTF_8), dataId, PDFGenerator.TIME_TO_LIVE_PREVIEW_CACHE);
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
        final GeneratePdfRequest request = GeneratePdfRequest.builder()
                .resultId(uid)
                .template(new GeneratePdfRequest.Content(dataId, GeneratePdfRequest.StorageType.CACHE))
                .document(new GeneratePdfRequest.Content(null, GeneratePdfRequest.StorageType.NOT_SET))
                .build();
        this.redisDAO.saveData(byteBuffer.array(), dataId, PDFGenerator.TIME_TO_LIVE_PREVIEW_CACHE);
        this.documentObserver.sendGeneratePdfRequest(request);
        return uid;
    }

    private boolean isValidUUID(final String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return false;
        }
        try {
            UUID.fromString(uuid);
        } catch (Throwable e) {
            return false;
        }
        return true;
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

    private Document generateDownloadableDocument(final UUID resultId) {
        waitForDocument(resultId);
        return loadStructuredDocumentFromCache(resultId);
    }

    private Document loadStructuredDocumentFromCache(final UUID resultId) {
        final String uid = resultId.toString();
        final byte[] cachedPDF = this.redisDAO.getDataByID(uid);
        final byte[] documentBytes = ChaCha20Utils.decrypt(cachedPDF);

        final String fileName = "DC-" + uid + ".pdf";

        return Document.newInstance(fileName, documentBytes);
    }

    private void waitForDocument(final UUID resultId) {
        final CountDownLatch count = new CountDownLatch(TRIALS);
        final String uid = resultId.toString();
        while (count.getCount() > 0) {
            if (this.redisDAO.exists(uid)) {
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
     * Create determine result id by document-id or smart-contract address
     *
     * @param hexString
     *          - Document Id or Smart-Contract address as HEX string
     * @return new UUID instance as result id
     */
    private UUID createResultId(final String hexString) {
        Preconditions.checkArgument(DocumentUtils.isHexadecimal(hexString), "Given string is not hexadecimal value");
        final byte[] data = Numeric.hexStringToByteArray(hexString);
        return UUID.nameUUIDFromBytes(Md5Utils.computeMD5Hash(data));
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
