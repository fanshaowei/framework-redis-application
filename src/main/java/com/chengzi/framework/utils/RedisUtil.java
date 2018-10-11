package com.chengzi.framework.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by fanshaowei on 2018-8-1.
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void strSet(String key, Object value, int expire){
        Optional.ofNullable(expire).ifPresent(exp -> redisTemplate.opsForValue().set(key, value,expire));

        redisTemplate.opsForValue().set(key, value);
    }

    public void strGet(String key){
        redisTemplate.opsForValue().get(key);
    }

    public  String strGetTtl(String key){
        String ttl = Optional.ofNullable(key).orElse(redisTemplate.opsForValue().getOperations().getExpire(key, TimeUnit.SECONDS).toString());

        return ttl;
    }
}
