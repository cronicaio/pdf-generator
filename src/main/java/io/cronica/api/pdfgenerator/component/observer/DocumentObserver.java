package io.cronica.api.pdfgenerator.component.observer;

import io.cronica.api.pdfgenerator.component.entity.DocumentStatus;

import java.util.Optional;

public interface DocumentObserver {

    /**
     * Register ID of document in ZooKeeper for further observing its status.
     *
     * @param documentID
     *          - unique ID of document
     */
    void putDocumentIDToObserve(String documentID);

    /**
     * Get status of document.
     *
     * @param documentID
     *          - unique ID of document
     * @return {@link DocumentStatus} object with current status of document ID.
     */
    Optional<DocumentStatus> check(String documentID);

    /**
     * Delete ZNode from ZooKeeper with specified ID of document in the path.
     *
     * @param documentID
     *          - unique ID of document
     */
    void deletePathWith(String documentID);
}
