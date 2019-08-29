package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.aws.AWSS3BucketAdapter;
import io.cronica.api.pdfgenerator.component.aws.Repeater;
import io.cronica.api.pdfgenerator.component.ca.CronicaCAAdapter;
import io.cronica.api.pdfgenerator.component.entity.Document;
import io.cronica.api.pdfgenerator.component.redis.RedisDAO;
import io.cronica.api.pdfgenerator.component.redis.RedisDocument;
import io.cronica.api.pdfgenerator.component.wrapper.TemplateContract;
import io.cronica.api.pdfgenerator.configuration.Beans;
import io.cronica.api.pdfgenerator.database.model.DocumentCertificate;
import io.cronica.api.pdfgenerator.database.repository.DocumentCertificateRepository;
import io.cronica.api.pdfgenerator.exception.DocumentNotFoundException;
import io.cronica.api.pdfgenerator.exception.InvalidRequestException;
import io.cronica.api.pdfgenerator.utils.ChaCha20Utils;
import io.cronica.api.pdfgenerator.utils.DocumentUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class PDFDocumentServiceImpl implements PDFDocumentService {

    private static final String PDF_DOCUMENT_TYPE = "pdf";
    private static final String ALGORITHM = "ChaCha20";

    private final Repeater repeater;

    private final RedisDAO redisDAO;

    private final CronicaCAAdapter cronicaCAAdapter;

    private final AWSS3BucketAdapter awss3BucketAdapter;

    private final DocumentTransactionService documentTransactionService;

    private final TemplateTransactionService templateTransactionService;

    private final DocumentCertificateRepository documentCertificateRepository;

    private final IssuerRegistryTransactionService issuerRegistryTransactionService;

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
            try {
                return generateStructuredDocument(docCert, redisDocument);
            }
            catch (Exception ex) {
                log.error("[SERVICE] exception while generating PDF of structured document", ex);
                return new Document();
            }
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

    private Document generateStructuredDocument(
            final DocumentCertificate docCertificate, final RedisDocument redisDocument) throws Exception {

        byte[] documentBytes;

        // if PDF is cached to Redis then return it, generate it otherwise
        if ( this.redisDAO.exists(redisDocument.getDocumentID()) ) {
            final byte[] cachedPDF = this.redisDAO.getPDFByID(redisDocument.getDocumentID());

            // decrypt cached PDF
            documentBytes = decrypt(cachedPDF);
        }
        else {
            final TemplateHandler templateHandler = getTemplateHandlerAccordingToFileType(docCertificate);
            templateHandler.generateTemplate();
            templateHandler.downloadAdditionalFiles();
            final InputStream documentInputStream = templateHandler.generatePDFDocument();

            // encrypt PDF before caching to Redis
            documentBytes = IOUtils.toByteArray(documentInputStream);
            final byte[] encryptedDocument = encrypt(documentBytes);
            this.redisDAO.savePDF(encryptedDocument, redisDocument.getDocumentID());
        }

        final String fileName = "DC-" + redisDocument.getDocumentID() + ".pdf";
        final byte[] signedDocument = this.cronicaCAAdapter.signDocument(documentBytes);

        return Document.newInstance(fileName, signedDocument);
    }

    private byte[] decrypt(final byte[] ciphertext) {
        final byte[] chacha20Key = Beans.chacha20SecretKey;
        final SecretKey secretKey = new SecretKeySpec(chacha20Key, 0, chacha20Key.length, ALGORITHM);
        return ChaCha20Utils.decrypt(ciphertext, secretKey);
    }

    private byte[] encrypt(final byte[] plaintext) {
        final byte[] chacha20Key = Beans.chacha20SecretKey;
        final SecretKey secretKey = new SecretKeySpec(chacha20Key, 0, chacha20Key.length, ALGORITHM);
        return ChaCha20Utils.encrypt(plaintext, secretKey);
    }

    private TemplateHandler getTemplateHandlerAccordingToFileType(final DocumentCertificate dc) {
        final String fileType = getFileType(dc);
        if (fileType.equals("html")) {
            return new HTMLTemplateHandler(
                    this.repeater, this.awss3BucketAdapter, dc, this.issuerRegistryTransactionService,
                    this.templateTransactionService, this.documentTransactionService);
        }
        else if (fileType.equals("jrxml")) {
            return new JasperSoftTemplateHandler(
                    this.repeater, this.awss3BucketAdapter, dc,
                    this.issuerRegistryTransactionService, this.templateTransactionService, this.documentTransactionService);
        }
        else {
            throw new RuntimeException("File with " + fileType + " extension is not supporting");
        }
    }

    private String getFileType(final DocumentCertificate docCertificate) {
        final String documentAddress = DocumentUtils.readDocumentAddress(docCertificate.getDocumentID());
        final String templateID = this.documentTransactionService.getTemplateID(documentAddress);
        final TemplateContract templateContract = this.templateTransactionService.loadTemplate(templateID);

        return this.templateTransactionService.getFileTypeOfTemplate(templateContract);
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
