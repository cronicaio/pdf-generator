package io.cronica.api.pdfgenerator.component.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import io.cronica.api.pdfgenerator.component.redis.RedisDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KryoSerializerImpl implements KryoSerializer {

    private final Kryo kryo;

    public KryoSerializerImpl() {
        this.kryo = new Kryo();
        this.kryo.register(RedisDocument.class);
    }

    /**
     * @see KryoSerializer#deserialize(byte[])
     */
    @Override
    public RedisDocument deserialize(final byte[] array) {
        try (Input input = new Input(array)) {
            log.debug("[KRYO] deserializing object to 'RedisDocument' object");
            return (RedisDocument) this.kryo.readClassAndObject(input);
        }
        catch (Exception ex) {
            log.error("[KRYO] exception while deserializing object", ex);
            return null;
        }
    }
}
