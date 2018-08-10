package com.chengzi.framework.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.io.ByteArrayOutputStream;

/**
 * Created by fanshaowei on 2018-8-9.
 */
public class KryoRedisSerializer<T> implements RedisSerializer {
    Logger logger = LoggerFactory.getLogger(KryoRedisSerializer.class);

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final ThreadLocal<Kryo> kryos = ThreadLocal.withInitial(Kryo::new);
    private Class<T> clazz;

    public KryoRedisSerializer(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(@Nullable Object o) throws SerializationException {
        if (o == null)
            return EMPTY_BYTE_ARRAY;

        Kryo kryo = kryos.get();
        kryo.setReferences(false);
        kryo.register(clazz);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, o);
        output.flush();

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
        if(bytes == null || bytes.length <=0)
            return null;

        Kryo kryo = kryos.get();
        kryo.setReferences(false);
        kryo.register(clazz);

        try {
            Input input = new Input(bytes);
            return  (T)kryo.readClassAndObject(input);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return null;
    }
}
