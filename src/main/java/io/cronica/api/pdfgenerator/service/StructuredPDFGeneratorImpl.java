package io.cronica.api.pdfgenerator.service;

import io.cronica.api.pdfgenerator.component.aws.AWSS3BucketAdapter;
import io.cronica.api.pdfgenerator.component.aws.Repeater;
import io.cronica.api.pdfgenerator.component.metrics.MethodID;
import io.cronica.api.pdfgenerator.component.metrics.MetricsLogger;
import io.cronica.api.pdfgenerator.component.redis.RedisDAO;
import io.cronica.api.pdfgenerator.component.wrapper.TemplateContract;
import io.cronica.api.pdfgenerator.database.model.DocumentCertificate;
import io.cronica.api.pdfgenerator.database.repository.DocumentCertificateRepository;
import io.cronica.api.pdfgenerator.exception.DocumentNotFoundException;
import io.cronica.api.pdfgenerator.utils.ChaCha20Utils;
import io.cronica.api.pdfgenerator.utils.DocumentUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Slf4j
@RequiredArgsConstructor
@Service
public class StructuredPDFGeneratorImpl implements StructuredPDFGenerator {

    private final Repeater repeater;

    private final RedisDAO redisDAO;

    private final MetricsLogger metricsLogger;

    private final AWSS3BucketAdapter awss3BucketAdapter;

    private final TemplateTransactionService templateTransactionService;

    private final DocumentTransactionService documentTransactionService;

    private final DocumentCertificateRepository documentCertificateRepository;

    private final IssuerRegistryTransactionService issuerRegistryTransactionService;

    /**
     * @see StructuredPDFGenerator#generateAndSave(String, String)
     */
    public void generateAndSave(final String documentID, @Nullable final String data) {
        final StopWatch stopWatch = new StopWatch();
        try {
            InputStream documentInputStream;
            if (DocumentUtils.isValidDocumentID(documentID)) {
                final DocumentCertificate docCert = getDocumentCertificateByID(documentID);
                final TemplateHandler templateHandler = getTemplateHandlerAccordingToFileType(docCert);
                templateHandler.generateTemplate();
                templateHandler.downloadAdditionalFiles();

                documentInputStream = templateHandler.generatePDFDocument();
                this.metricsLogger.incrementCount(MethodID.COUNT_OF_SUCCESSFUL_DOCUMENT_GENERATIONS);
                this.metricsLogger.logExecutionTime(MethodID.TIME_OF_DOCUMENT_GENERATION, stopWatch.getTotalTimeMillis());
            } else {
                final TemplateHandler templateHandler = this.getHTMLTemplateHandlerForThumbnail(documentID, data);
                templateHandler.generateTemplate();
                templateHandler.downloadAdditionalFiles();

                documentInputStream = templateHandler.generatePDFDocument();
            }
            // encrypt PDF before caching to Redis
            final byte[] documentBytes = IOUtils.toByteArray(documentInputStream);
            final byte[] encryptedDocument = ChaCha20Utils.encrypt(documentBytes);
            this.redisDAO.savePDF(encryptedDocument, documentID);
        } catch (Exception ex) {
            log.error("[SERVICE] exception while generating PDF with '{}' ID", documentID, ex);
            this.metricsLogger.incrementCount(MethodID.COUNT_OF_FAILED_DOCUMENT_GENERATIONS);
        } finally {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
        }
    }

    /**
     * @see StructuredPDFGenerator#generate(ByteBuffer)
     */
    @Override
    public byte[] generate(ByteBuffer templateZip) {
        try {
            final HTMLTemplateHandlerChainless templateHandlerChainless = new HTMLTemplateHandlerChainless(templateZip);
            templateHandlerChainless.generateTemplate();
            final InputStream documentInputStream = templateHandlerChainless.generatePDFDocument();
            return IOUtils.toByteArray(documentInputStream);
        } catch (Exception ex) {
            log.error("[SERVICE] exception while generating preview PDF", ex);
            return new byte[0];
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

    private TemplateHandler getHTMLTemplateHandlerForThumbnail(final String templateId, final String jsonData) {
        return new HTMLTemplateHandler(
                this.repeater, this.awss3BucketAdapter, this.issuerRegistryTransactionService,
                this.templateTransactionService, templateId, jsonData != null ? jsonData : "{}");
    }

    private String getFileType(final DocumentCertificate docCertificate) {
        final String documentAddress = DocumentUtils.readDocumentAddress(docCertificate.getDocumentID());
        final String templateID = this.documentTransactionService.getTemplateID(documentAddress);
        final TemplateContract templateContract = this.templateTransactionService.loadTemplate(templateID);

        return this.templateTransactionService.getFileTypeOfTemplate(templateContract);
    }
}
