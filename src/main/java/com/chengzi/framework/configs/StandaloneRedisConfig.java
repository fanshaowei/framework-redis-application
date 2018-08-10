package com.chengzi.framework.configs;

import com.alibaba.fastjson.parser.ParserConfig;
import com.chengzi.framework.serializer.FastJsonRedisSerializer;
import com.chengzi.framework.serializer.StringRedisSerializer;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.Pool;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.lang.Nullable;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.time.Duration;

/**
 * Created by fanshaowei on 2018-8-1.
 */
@Configuration
@PropertySource("classpath:redis.properties")
public class StandaloneRedisConfig {
    @Value("${redis.hostName}")
    private String hostName;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.maxIdle}")
    private Integer maxIdle;

   @Value("${redis.minIdle}")
    private Integer minIdle;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.blockWhenExhausted}")
    private boolean blockWhenExhausted;

    @Value("${redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;

    @Value("${redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;

    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.testOnReturn}")
    private boolean testOnReturn;

    @Value("${redis.testWhileIdle}")
    private boolean testWhileIdle;

    public void setJedisPoolConfig(GenericObjectPoolConfig jedisPoolConfig){
        //JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
         /**在不新建连接的情况下，连接池中保持能保持空闲的大连接数**/
        jedisPoolConfig.setMaxIdle(maxIdle);
        /**在不新建连接的情况下，连接池中保持能保持空闲的最小连接数**/
        jedisPoolConfig.setMinIdle(minIdle);
        /**最大连接数**/
        jedisPoolConfig.setMaxTotal(maxTotal);
        /**获取连接时的最大等待毫秒数，和setBlockWhenExhausted参数配合使用，如果超时，报异常**/
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        /**当连接池中没有可用连接数时，true表示阻塞等待，直到超过maxWaitMillis设置的大小; false表示直接报异常**/
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        /**连接保持空闲而不被驱逐的最长时间**/
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        /**最大空闲时间**/
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        /**表示从连接池中获取连接时，是否提前执行validate操作，如果返回true,则返回的连接都是可用的**/
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        //在return连接给pool时，是否提前进行validate操作
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        /**表示idle object evitor两次扫描之间要sleep的毫秒数**/
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        /**如果为true，表示有一个idle object evitor线程对idle object进行扫描，如果validate失败，此object会被从pool中drop掉；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；**/
        //jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        /**在minEvictableIdleTimeMillis基础上，加入了至少minIdle个对象已经在pool里面了。如果为-1，evicted不会根据idle time驱逐任何对象。如果minEvictableIdleTimeMillis>0，则此项设置无意义，且只有在timeBetweenEvictionRunsMillis大于0时才有意义；**/
        //jedisPoolConfig.setSoftMinEvictableIdleTimeMillis();
    }

    /**
     * 创建jedis客户配置
     * @return
     */
    @Bean
    public JedisClientConfiguration jedisClientConfiguration(){
        return JedisClientConfiguration.builder().build();
    }

    /**
     * 配置redis单例连接服务端信息
     * @return
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);

        return redisStandaloneConfiguration;
    }

    /**
     * 创建jedis连接工厂
     * @param redisStandaloneConfiguration
     * @param jedisClientConfiguration
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration, JedisClientConfiguration jedisClientConfiguration){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration);
        GenericObjectPoolConfig genericObjectPoolConfig = jedisConnectionFactory.getPoolConfig();
        setJedisPoolConfig(genericObjectPoolConfig);

        return jedisConnectionFactory;
    }

    /**
     * 创建一个redis模版
     * @param jedisConnectionFactory
     * @return RedisTemplate<String,Object> redisTemplate
     */
    @Bean(name = "redisTemplate1")
    public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String,Object > redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setEnableDefaultSerializer(true);

        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        ParserConfig.getGlobalInstance().addAccept("com.chengz.");

        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
