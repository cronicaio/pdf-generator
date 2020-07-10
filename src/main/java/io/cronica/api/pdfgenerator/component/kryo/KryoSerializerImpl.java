package io.cronica.api.pdfgenerator.component.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.cronica.api.pdfgenerator.component.redis.RedisDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Slf4j
@Component
public class KryoSerializerImpl implements KryoSerializer {

    private final Kryo kryo;

    public KryoSerializerImpl() {
        this.kryo = new Kryo();
        this.kryo.register(RedisDocument.class, 100);
    }

    @Override
    public byte[] serialize(final RedisDocument redisDocument) {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final Output output = new Output(stream);
        this.kryo.writeClassAndObject(output, redisDocument);
        output.close();

        return stream.toByteArray();
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
