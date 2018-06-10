package com.yhxx.wxappboot.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yhxx.common.bean.Money;
import com.yhxx.common.utils.jsonToolUtils.EnumJsonDeserializer;
import com.yhxx.common.utils.jsonToolUtils.EnumJsonSerializer;
import com.yhxx.common.utils.jsonToolUtils.MoneyJsonDeserializer;
import com.yhxx.common.utils.jsonToolUtils.MoneyJsonSerializer;
import com.yhxx.common.utils.redisToolUtils.redis.RedisCacheFactory;
import com.yhxx.common.utils.redisToolUtils.util.DefaultThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: Wanglf
 * @Date: Created in 17:19 2018/6/9
 * @modified By:
 */
@Configuration
public class CachingConfiguration {

    @Value("${redis.hostname}")
    private String hostName;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.dbIndex}")
    private int dbIndex;

    @Value("${redis.name.prefix}")
    private String namePrefix;

    @Value("${redis.password}")
    private String password;

    private static ObjectMapper redisObjectMapper = null;

    /**
     * 此处避免Req，Resp，Redis等多处使用json转换因此不作为spring单例bean处理
     *
     * @return
     */
    private ObjectMapper createObjectMapper() {
        if (redisObjectMapper == null) {
            ObjectMapper mapper = new ObjectMapper();

            SimpleModule module = new SimpleModule();
            // 转换金额
            module.addSerializer(Money.class, new MoneyJsonSerializer());
            module.addDeserializer(Money.class, new MoneyJsonDeserializer());

            // 转换枚举TypeBean,StringTypeBean
            module.addSerializer(Enum.class, new EnumJsonSerializer());
            module.addDeserializer(Enum.class, new EnumJsonDeserializer());

            mapper.registerModule(module);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            redisObjectMapper = mapper;
        }

        return redisObjectMapper;
    }


    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(hostName);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setDatabase(dbIndex);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return jedisConnectionFactory;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxWaitMillis(1000);
        jedisPoolConfig.setTestOnBorrow(true);
        return jedisPoolConfig;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());

        return stringRedisTemplate;
    }

    @Bean
    public DefaultThreadFactory defaultThreadFactory() {
        return new DefaultThreadFactory();
    }

    @Bean
    public RedisCacheFactory cacheFactory() {
        RedisCacheFactory cacheFactory = new RedisCacheFactory();
        cacheFactory.setDelayEvictionThreadFactory(defaultThreadFactory());
        cacheFactory.setAppName(namePrefix);
        cacheFactory.setStringRedisTemplate(stringRedisTemplate());

        return cacheFactory;
    }

    //正常使用一个测试的reids


}

