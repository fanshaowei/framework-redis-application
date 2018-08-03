package com.chengzi.framework.configs;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanshaowei on 2018-8-3.
 */
@Configuration
public class ShardRedisConfig {
    @Bean
    public ShardedJedisPool getShardJedisPool(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(10);

        JedisShardInfo jedisShardInfo_A = new JedisShardInfo("127.0.0.1", 6379);
        JedisShardInfo jedisShardInfo_B = new JedisShardInfo("192.168.174.129", 6379);
        JedisShardInfo jedisShardInfo_C = new JedisShardInfo("192.168.174.129", 6380);

        shards.add(jedisShardInfo_A);
        shards.add(jedisShardInfo_B);
        shards.add(jedisShardInfo_C);

        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shards);

        return shardedJedisPool;
    }

    public void getShardedJedisByKey(String key){
        ShardedJedis shardedJedis = getShardJedisPool().getResource();
        shardedJedis.getShard(key);
        shardedJedis.getShardInfo(key);
    }
}
