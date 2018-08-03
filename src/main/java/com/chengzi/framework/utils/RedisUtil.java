package com.chengzi.framework.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by fanshaowei on 2018-8-1.
 */
public class RedisUtil {
    private RedisTemplate<String,Object> redisTemplate;

    public RedisUtil(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void stringSet(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }
}
