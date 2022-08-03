package io.cronica.api.pdfgenerator.component.observer;

import io.cronica.api.pdfgenerator.component.entity.DocumentStatus;
import io.cronica.api.pdfgenerator.component.kafka.entities.GeneratePdfRequest;
import io.cronica.api.pdfgenerator.component.kafka.stream.GeneratePdfStream;
import io.cronica.api.pdfgenerator.service.PDFGenerator;
import io.cronica.api.pdfgenerator.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@EnableBinding({GeneratePdfStream.class})
public class DocumentObserverImpl implements DocumentObserver {

    private final CuratorFramework curator;

    private final PDFGenerator structuredPdfGenerator;
    private final PDFGenerator nonStructuredPdfGenerator;
    private final GeneratePdfStream stream;

    public DocumentObserverImpl(
            final CuratorFramework curator,
            @Qualifier("structuredPDFGenerator") final PDFGenerator structuredPdfGenerator,
            @Qualifier("nonStructuredPDFGenerator") final PDFGenerator nonStructuredPdfGenerator,
            final GeneratePdfStream stream
    ) {
        this.curator = curator;
        this.structuredPdfGenerator = structuredPdfGenerator;
        this.nonStructuredPdfGenerator = nonStructuredPdfGenerator;
        this.stream = stream;
    }

    @PostConstruct
    public void init() throws Exception {
        try {
            if ( !exists(Constants.DOCUMENTS_ROOT) ) {
                this.curator.create().forPath(Constants.DOCUMENTS_ROOT);
            }
        }
        catch (KeeperException.NodeExistsException ex) {
            log.debug("[ZOOKEEPER] ZNode under '{}' path already exists", Constants.DOCUMENTS_ROOT);
        }
    }

    @StreamListener(GeneratePdfStream.REQUEST)
    public void observe_t(@Payload final GeneratePdfRequest request) {
        log.info("Receive {}", request);
        this.setStatus(path(request.getResultId().toString()), DocumentStatus.PENDING);
        log.info("Received message of document type: {}", request.getDocument().getStorageType());
        if (request.getDocument().getStorageType() == GeneratePdfRequest.StorageType.S3) {
            log.info("Received message of document type 'Non Structured'");
            this.nonStructuredPdfGenerator.generateAndSave(request);
        } else {
            log.info("Received message of document type 'Structured'");
            this.structuredPdfGenerator.generateAndSave(request);
        }
        this.setStatus(path(request.getResultId().toString()), DocumentStatus.GENERATED);
    }

    @Override
    public void sendGeneratePdfRequest(final GeneratePdfRequest request) {
        final Message message = MessageBuilder.withPayload(request)
                .setHeader(KafkaHeaders.MESSAGE_KEY, request.getResultId().toString())
                .build();
        this.putDocumentIDToObserve(request.getResultId().toString());
        this.stream.generatePdfRequest().send(message);
        log.info("Submit {}", request);
    }

    @Scheduled(fixedDelay = 1000)
    public void cleanup() {
        try {
            final List<String> list = this.curator.getChildren()
                    .forPath(Constants.DOCUMENTS_ROOT)
                    .stream()
                    .filter(documentID -> {
                        final byte[] data = readDataByPath(path(documentID)).orElseThrow(() -> new IllegalStateException("Data not found"));
                        return Arrays.equals(DocumentStatus.GENERATED.getRawStatus(), data);
                    })
                    .collect(Collectors.toList());
            list.forEach(this::deletePathWith);
            if (!list.isEmpty()) {
                log.debug("[OBSERVER] Cleaned {} paths with GENERATED status", list.size());
            }
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while observing documents status", ex);
        }
    }

    private void setStatus(final String path, final DocumentStatus status) {
        log.debug("[OBSERVER] update document under '{}' path. New status: '{}'", path, status.getStatus());
        try {
            this.curator.setData().forPath(path, status.getRawStatus());
        }
        catch (KeeperException.NoNodeException ex) {
            log.debug("[OBSERVER] ZNode under '{}' path does not exists", path);
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while writing to ZNode under '{}' path", path, ex);
        }
    }

    private void putDocumentIDToObserve(final String documentID) {
        final String path = path(documentID);
        log.debug("[OBSERVER] registering transaction by '{}' path", path);
        try {
            this.curator.create().forPath(path, DocumentStatus.REGISTERED.getRawStatus());
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while registering document by '{}' path", path, ex);
        }
    }

    private boolean exists(final String path) {
        try {
            final Stat stat = this.curator.checkExists().forPath(path);
            return Objects.nonNull(stat);
        }
        catch (Exception ex) {
            return false;
        }
    }

    private Optional<byte[]> readDataByPath(final String path) {
        log.debug("[OBSERVER] reading data by '{}' path", path);
        try {
            return Optional.of(this.curator.getData().forPath(path));
        }
        catch (KeeperException.NoNodeException ex) {
            log.debug("[OBSERVER] ZNode by '{}' path does not exists", path);
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while reading data by '{}' path", path, ex);
        }
        return Optional.empty();
    }

    /**
     * @see DocumentObserver#check(String)
     */
    @Override
    public Optional<DocumentStatus> check(final String documentID) {
        final String path = path(documentID);
        try {
            if ( exists(path) ) {
                final byte[] data = readDataByPath(path).orElseThrow();
                if ( Arrays.equals(data, DocumentStatus.GENERATED.getRawStatus()) ) {
                    return Optional.of(DocumentStatus.GENERATED);
                }
                else if ( Arrays.equals(data, DocumentStatus.PENDING.getRawStatus()) ) {
                    return Optional.of(DocumentStatus.PENDING);
                }
                else {
                    return Optional.of(DocumentStatus.REGISTERED);
                }
            }
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while checking transaction receipt by '{}' hash", documentID, ex);
        }
        return Optional.empty();
    }

    public void deletePathWith(final String documentID) {
        final String path = path(documentID);
        log.debug("[OBSERVER] deleting path '{}'", path);
        try {
            this.curator.delete().deletingChildrenIfNeeded().forPath(path);
        }
        catch (KeeperException.NoNodeException ex) {
            log.debug("[OBSERVER] ZNode under '{}' path does not exists", path);
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while deleting path: '{}'", path, ex);
        }
    }

    private String path(final String documentID) {
        return Constants.DOCUMENTS_ROOT + Constants.SEPARATOR + documentID;
    }

}
