package io.cronica.api.pdfgenerator.component.redis;

import io.cronica.api.pdfgenerator.component.kryo.KryoSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
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
    public Optional<RedisDocument> get(final String key) {
        log.info("[REDIS] reading object under '{}' key", key);
        final RBinaryStream rBinaryStream = this.redissonClient.getBinaryStream(key);
        log.info("[REDIS] found object under '{}' key", key);
        return Optional.ofNullable(this.kryoSerializer.deserialize(rBinaryStream.get()));
    }

    /**
     * @see RedisDAO#save(String, RedisDocument, Duration)
     */
    @Override
    public void save(final String key, final RedisDocument redisDocument, final Duration expire) {
        log.info("[REDIS] saving '{}' link under key '{}' ", redisDocument, key);
        final byte[] array = this.kryoSerializer.serialize(redisDocument);
        final RBinaryStream rBinaryStream = this.redissonClient.getBinaryStream(key);
        rBinaryStream.set(array);
        rBinaryStream.expire(expire.getSeconds(), TimeUnit.SECONDS);
        log.info("[REDIS] '{}' object has been saved under '{}' key", redisDocument, key);
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
     * @see RedisDAO#saveData(byte[], String, Duration)
     */
    @Override
    public boolean saveData(final byte[] document, final String key, final Duration expire) {
        log.info("[REDIS] saving data under key '{}' ", key);
        final RBinaryStream rBinaryStream = this.redissonClient.getBinaryStream(key);
        rBinaryStream.set(document);
        rBinaryStream.expire(expire.getSeconds(), TimeUnit.SECONDS);
        log.info("[REDIS] data has been saved under '{}' key", key);
        return true;
    }

    /**
     * @see RedisDAO#getDataByID(String)
     */
    @Override
    public byte[] getDataByID(final String key) {
        log.info("[REDIS] reading data under '{}' key", key);
        final RBinaryStream rBinaryStream = this.redissonClient.getBinaryStream(key);
        log.info("[REDIS] found data under '{}' key", key);
        return rBinaryStream.get();
    }
}
