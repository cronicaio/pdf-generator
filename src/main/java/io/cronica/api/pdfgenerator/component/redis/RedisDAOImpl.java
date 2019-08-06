package io.cronica.api.pdfgenerator.component.redis;

import io.cronica.api.pdfgenerator.component.kryo.KryoSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

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
        log.info("[REDIS] reading object with '{}' key", key);
        if ( exists(key) ) {
            final RBinaryStream rBinaryStream = this.redissonClient.getBinaryStream(key);
            log.info("[REDIS] found object under '{}' key", key);
            return this.kryoSerializer.deserialize(rBinaryStream.get());
        }

        log.info("[REDIS] object with '{}' does not exists", key);
        return null;
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
}
