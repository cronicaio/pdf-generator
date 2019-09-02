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
                    .peek(documentID -> {
                        final String path = path(documentID);
                        final byte[] data = readDataByPath(path);
                        if ( Arrays.equals(DocumentStatus.REGISTERED.getRawStatus(), data) ) {
                            setStatus(path, DocumentStatus.PENDING);
                            this.structuredPDFGenerator.generateAndSave(documentID);
                            setStatus(path, DocumentStatus.GENERATED);
                        }
                    })
                    .collect(Collectors.toList());
            log.debug("[OBSERVER] {} sub paths found. Result: {}", list.size(), list);
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

    private boolean exists(final String path) {
        try {
            final Stat stat = this.curator.checkExists().forPath(path);
            return Objects.nonNull(stat);
        }
        catch (Exception ex) {
            return false;
        }
    }

    private byte[] readDataByPath(final String path) {
        log.debug("[OBSERVER] reading data by '{}' path", path);
        try {
            return this.curator.getData().forPath(path);
        }
        catch (KeeperException.NoNodeException ex) {
            log.debug("[OBSERVER] ZNode by '{}' path does not exists", path);
        }
        catch (Exception ex) {
            log.error("[OBSERVER] exception while reading data by '{}' path", path, ex);
        }
        return null;
    }

    /**
     * @see DocumentObserver#check(String)
     */
    @Override
    public Optional<DocumentStatus> check(final String documentID) {
        final String path = path(documentID);
        try {
            if ( exists(path) ) {
                final byte[] data = readDataByPath(path);
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
            this.curator.delete().forPath(path);
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
