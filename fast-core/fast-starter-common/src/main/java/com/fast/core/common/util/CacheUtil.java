package com.fast.core.common.util;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 缓存管理
 *
 * @Author: 黄嘉浩
 * @Date: 2023-06-15 14:53
 **/
public class CacheUtil {
    private static CacheManager cacheManager;

    /**
     * 获取缓存工具
     *
     * @return CacheManager
     */
    private static CacheManager getCacheManager() {
        if (cacheManager == null) {
            cacheManager = SpringUtil.getBean(CacheManager.class);
        }
        return cacheManager;
    }

    /**
     * 获取缓存
     **/
    public static Cache getCache(String cacheName) {
        return getCacheManager().getCache(cacheName);
    }

    /**
     * 清除缓存
     *
     * @param cacheName:    缓存名
     * @param automaticKey: 是否是 自动生成缓存键
     * @param methodName:   方法名称
     * @param params:
     **/
    public static void clearCache(String cacheName, Boolean automaticKey, String methodName, Object... params) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            return;
        }
        String cacheKey = generateCacheKey(methodName, automaticKey, params);
        cache.evict(cacheKey);
    }

    private static String generateCacheKey(String methodName, Boolean automaticKey, Object... params) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(methodName);
        for (Object param : params) {
            if (automaticKey) {
                keyBuilder.append("-");
            } else {
                keyBuilder.append(":");
            }
            keyBuilder.append(param.toString());
        }
        return keyBuilder.toString();
    }
}
