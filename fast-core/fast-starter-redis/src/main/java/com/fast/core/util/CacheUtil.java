package com.fast.core.util;

import org.springframework.stereotype.Component;

/**
 * 基于Redis的缓存
 *
 * @author 黄嘉浩
 * @date 2023-08-11 15:56
 **/
@Component
public class CacheUtil {
    private static FastRedis redis;

    public CacheUtil(FastRedis redis) {
        CacheUtil.redis = redis;
    }

    /**
     * 设置缓存
     *
     * @param key    关键
     * @param value  价值
     * @param expire 到期
     */
    public static void put(String key, Object value, long expire) {
        redis.setObject(key, value, expire);
    }

    /**
     * 设置缓存
     *
     * @param key   关键
     * @param value 价值
     */
    public static void put(String key, Object value) {
        redis.setObject(key, value);
    }

    /**
     * 获取缓存
     *
     * @param key   关键
     * @param clazz clazz
     * @return {@link T}
     */
    public static <T> T get(String key, Class<T> clazz) {
        return redis.getObject(key, clazz);
    }

    /**
     * 清除缓存
     *
     * @param cacheName 缓存名称
     */
    public static boolean clearCache(String cacheName) {
        return redis.deleteKey(cacheName);
    }

    /**
     * 清除缓存
     * 多用于注解类型的删除
     *
     * @param cacheName 缓存名称
     * @param params    参数个数
     * @return boolean
     */
    public static boolean clearCache(String cacheName, Object... params) {
        return redis.deleteKey(parameterConversion(cacheName, params));
    }

    /**
     * 处理参数拼接
     *
     * @param cacheName 方法名称
     * @param params    参数个数
     * @return {@link String} 缓存键
     */
    private static String parameterConversion(String cacheName, Object... params) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(cacheName);
        for (Object param : params) {
            keyBuilder.append("::");
            keyBuilder.append(param.toString());
        }
        return keyBuilder.toString();
    }

}
