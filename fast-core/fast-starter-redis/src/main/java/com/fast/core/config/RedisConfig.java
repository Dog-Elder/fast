package com.fast.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 复述,配置
 *
 * @author 黄嘉浩
 * @date 2023/08/18
 */
@Configuration
public class RedisConfig {

    /**
     * 复述,模板
     *
     * @param factory                     工厂
     * @param jackson2JsonRedisSerializer jackson2 json复述,序列化器
     * @return {@link RedisTemplate}<{@link String}, {@link Object}>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //  key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);

        //  hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);

        //  value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);

        //  hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }


}