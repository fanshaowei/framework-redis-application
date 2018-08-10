package com.chengzi.framework.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

/**
 * Created by fanshaowei on 2018-8-7.
 * 用FastJson实现序列化，用于redis value编解码
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz){
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return ofNullable(t)
                .map(r -> JSON.toJSONString(r, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET))
                .orElseGet(() -> new byte[0]);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return Optional.ofNullable(bytes)
                .map(t -> JSON.parseObject(new String(t, DEFAULT_CHARSET), clazz))
                .orElseGet(() -> null);
    }

}
