package io.cronica.api.pdfgenerator.component.observer;

import io.cronica.api.pdfgenerator.component.entity.DocumentStatus;
import io.cronica.api.pdfgenerator.service.StructuredPDFGenerator;
import io.cronica.api.pdfgenerator.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class DocumentObserverImpl implements DocumentObserver {

    private final CuratorFramework curator;

    private final StructuredPDFGenerator structuredPDFGenerator;

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

    @Scheduled(fixedDelay = 500)
    public void observe() {
        try {
            final List<String> list = this.curator.getChildren()
                    .forPath(Constants.DOCUMENTS_ROOT)
                    .stream()
                    .filter(documentID -> {
                        final byte[] data = readDataByPath(path(documentID)).orElseThrow(() -> new IllegalStateException("Data not found"));
                        return Arrays.equals(DocumentStatus.REGISTERED.getRawStatus(), data);
                    })
                    .peek(documentID -> {
                        setStatus(path(documentID), DocumentStatus.PENDING);
                        CompletableFuture
                                .runAsync(() -> this.processDocumentCommand(documentID))
                                .exceptionally(ex -> {
                                    log.error("[OBSERVER] Error while generating document", ex);
                                    return null;
                                });
                    })
                    .collect(Collectors.toList());
            if (!list.isEmpty()) {
                log.debug("[OBSERVER] {} sub paths found. Result: {}", list.size(), list);
            }
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while observing documents status", ex);
        }
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

    private void processDocumentCommand(final String documentId) {
        final String jsonData = readDataByPath(dataPath(documentId))
                .map(String::new)
                .orElse(null);
        this.structuredPDFGenerator.generateAndSave(documentId, jsonData);
        setStatus(path(documentId), DocumentStatus.GENERATED);
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

    /**
     * @see DocumentObserver#putDocumentIDToObserve(String)
     */
    @Override
    public void putDocumentIDToObserve(final String documentID) {
        final String path = path(documentID);
        log.debug("[OBSERVER] registering transaction by '{}' path", path);
        try {
            this.curator.create().forPath(path, DocumentStatus.REGISTERED.getRawStatus());
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while registering document by '{}' path", path);
        }
    }

    /**
     * @see DocumentObserver#putDocumentIDToObserve(String, String)
     */
    @Override
    public void putDocumentIDToObserve(final String documentID, final String data) {
        this.putDocumentIDToObserve(documentID);
        final String dataPath = dataPath(documentID);
        log.debug("[OBSERVER] put data into '{}' path", dataPath);
        try {
            this.curator.create().forPath(dataPath, data.getBytes());
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while registering document by '{}' path", dataPath);
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

    /**
     * @see DocumentObserver#deletePathWith(String)
     */
    @Override
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

    private String dataPath(final String documentID) {
        return path(documentID) + Constants.SEPARATOR + "data";
    }
}
