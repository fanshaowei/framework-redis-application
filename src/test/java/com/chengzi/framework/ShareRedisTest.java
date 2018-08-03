package com.chengzi.framework;

import com.chengzi.framework.configs.ShardRedisConfig;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Client;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.ShardInfo;

import java.util.Optional;

/**
 * Created by fanshaowei on 2018-8-3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShareRedisTest {
    @Autowired
    ShardRedisConfig shardRedisConfig;

    private static final String PREX = "fsw";

    @Test
    public void tesShadJedisPool(){
        String key = null;
        for (int i=50;i>0; i--){
            key = Integer.toString(i);
            ShardedJedis shardedJedis = shardRedisConfig.getShardJedisPool().getResource();

            System.out.println(PREX + key + ": " +shardedJedis.getShard(PREX + key).getClient().getHost()+":"+shardedJedis.getShard(PREX + key).getClient().getPort());

            shardedJedis.set(PREX + key, key);
            shardedJedis.close();
        }

    }
}
