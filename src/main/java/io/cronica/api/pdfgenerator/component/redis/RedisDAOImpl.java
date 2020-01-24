package io.cronica.api.pdfgenerator.component.redis;

import io.cronica.api.pdfgenerator.component.kryo.KryoSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisDAOImpl implements RedisDAO {

    private final RedissonClient redissonClient;

    private final KryoSerializer kryoSerializer;

    /**
     * @see RedisDAO#get(String)
     */
    @Override
    public RedisDocument get(final String key) {
        log.info("[REDIS] reading object under '{}' key", key);
        final RBinaryStream rBinaryStream = this.redissonClient.getBinaryStream(key);
        log.info("[REDIS] found object under '{}' key", key);
        return this.kryoSerializer.deserialize(rBinaryStream.get());
    }

    /**
     * @see RedisDAO#exists(String)
     */
    @Override
    public boolean exists(final String key) {
        log.debug("[REDIS] check whether exists object under '{}' key", key);
        final RBinaryStream rBinaryStream = this.redissonClient.getBinaryStream(key);
        return rBinaryStream.isExists() && rBinaryStream.size() != 0;
    }

    /**
     * @see RedisDAO#savePDF(byte[], String, Duration)
     */
    @Override
    public boolean savePDF(final byte[] document, final String key, final Duration expire) {
        log.info("[REDIS] saving PDF document under key '{}' ", key);
        final RBinaryStream rBinaryStream = this.redissonClient.getBinaryStream(key);
        rBinaryStream.set(document);
        rBinaryStream.expire(expire.getSeconds(), TimeUnit.SECONDS);
        log.info("[REDIS] PDF document has been saved under '{}' key", key);
        return true;
    }

    /**
     * @see RedisDAO#getPDFByID(String)
     */
    @Override
    public byte[] getPDFByID(final String key) {
        log.info("[REDIS] reading PDF document under '{}' key", key);
        final RBinaryStream rBinaryStream = this.redissonClient.getBinaryStream(key);
        log.info("[REDIS] found PDF document under '{}' key", key);
        return rBinaryStream.get();
    }
}
