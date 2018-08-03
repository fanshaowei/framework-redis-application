package com.chengzi.framework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by fanshaowei on 2018-8-2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("mySet","fsw");

        System.out.println(redisTemplate.opsForValue().get("mySet"));
    }
}
