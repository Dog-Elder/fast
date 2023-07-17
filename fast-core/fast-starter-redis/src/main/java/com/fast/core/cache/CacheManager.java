package com.fast.core.cache;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 缓存管理器
 * @author 黄嘉浩
 */
public class CacheManager extends RedisCacheManager {
    public CacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        //  获取缓存名称中指定的过期时间
        Duration expiration = parseExpiration(name);

        //  如果缓存名称中指定了过期时间，则设置对应的过期时间
        if (expiration != null) {
            cacheConfig = cacheConfig.entryTtl(expiration);
        }
        //  删除 "@ttl=**" 部分
        return super.createRedisCache(name.replaceAll("@ttl=\\d+", ""), cacheConfig);
    }

    private Duration parseExpiration(String name) {
        //  定义过期时间的正则表达式，匹配格式为 "@ttl=30" 的字符串
        String pattern = "@ttl=(\\d+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(name);

        if (matcher.find()) {
            //  如果匹配成功，提取过期时间的值并转换为秒数
            int ttlValue = Integer.parseInt(matcher.group(1));
            //  进行其他操作或生成缓存键
            return Duration.ofSeconds(ttlValue);
        } else {
            //  如果没有匹配到过期时间的信息，则返回 null 表示不设置过期时间
            return null;
        }
    }
}