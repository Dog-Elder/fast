package com.fast.core.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 缓存配置
 *
 * @author 黄嘉浩
 * @date 2023/08/18
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * 复述,缓存配置
     * Redis缓存配置
     *
     * @return {@link RedisCacheConfiguration}
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(Jackson2JsonRedisSerializer jsonRedisSerializer) {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer));

        return cacheConfiguration;
    }

    /**
     * 缓存管理器
     *
     * @param connectionFactory  连接工厂
     * @param cacheConfiguration 缓存配置 {@link CacheConfig#redisCacheConfiguration()}
     * @return {@link CacheManager}
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, RedisCacheConfiguration cacheConfiguration) {
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        com.fast.core.cache.CacheManager cacheManager = new com.fast.core.cache.CacheManager(cacheWriter, cacheConfiguration);
        return cacheManager;
    }

}