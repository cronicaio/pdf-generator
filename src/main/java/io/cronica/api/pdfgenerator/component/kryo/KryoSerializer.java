package io.cronica.api.pdfgenerator.component.kryo;

import io.cronica.api.pdfgenerator.component.redis.RedisDocument;

public interface KryoSerializer {

    /**
     * Serialize {@link RedisDocument} object to byte array.
     *
     * @param redisDocument
     *          - object which necessary serialize
     * @return array of bytes
     */
    byte[] serialize(RedisDocument redisDocument);

    /**
     * Deserialize array of bytes into {@link RedisDocument} object.
     *
     * @param array
     *          - array of bytes which necessary to deserialize
     * @return {@link RedisDocument} object
     */
    RedisDocument deserialize(byte[] array);
}
