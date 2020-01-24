package io.cronica.api.pdfgenerator.component.redis;

import java.time.Duration;

public interface RedisDAO {

    /**
     * Get {@link RedisDocument} with specified key.
     *
     * @param key
     *          - key to record
     * @return {@link RedisDocument} object saved under specified key
     */
    RedisDocument get(String key);

    /**
     * Check whether {@link RedisDocument} with specified key exists.
     *
     * @param key
     *          - key to record
     * @return {@code true} if exists, {@code false} otherwise
     */
    boolean exists(String key);

    /**
     * Save specified PDF document to Redis under specified key.
     *
     * @param document
     *          - PDF document in the representation of byte array
     * @param key
     *          - key to PDF
     * @param expire
     *          - expiration of pdf document when it will be automatically deleted
     * @return {@code true} if successfully saved, {@code false} otherwise
     */
    boolean savePDF(byte[] document, String key, Duration expire);

    /**
     * Read PDF document from Redis identified by document ID.
     *
     * @param key
     *          - key to PDF
     * @return file as array of bytes
     */
    byte[] getPDFByID(String key);
}
