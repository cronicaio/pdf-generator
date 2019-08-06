package io.cronica.api.pdfgenerator.component.redis;

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
     * @return 'true' if exists, 'false' otherwise
     */
    boolean exists(String key);
}
