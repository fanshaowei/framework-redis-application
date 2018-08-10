package com.chengzi.framework.serializer;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Created by fanshaowei on 2018-8-8.
 */
public class StringRedisSerializer implements RedisSerializer<Object>{
    private final Charset charset;
    private final String target = "\"";
    private String replacement = "";

    public StringRedisSerializer(){
        this(Charset.forName("UTF8"));
    }

    public StringRedisSerializer(Charset charset) {
        Assert.notNull(charset,"Charset must no be null");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(@Nullable Object o) throws SerializationException {
        return Optional.ofNullable(o)
                .map(r -> JSON.toJSONString(r).replace(target, replacement).getBytes())
                .orElseGet(() -> null);
    }

    @Override
    public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
        return Optional.ofNullable(bytes)
                .map(r -> new String(r, charset))
                .orElseGet(() ->null);
    }
}
