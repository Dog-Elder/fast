package com.fast.core.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 用于缓存方法
 * 方法key生成器
 *
 * @author 黄嘉浩
 */
@Component
public class MethodKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        // 自定义缓存键的生成逻辑
        // 根据类名、方法名和参数生成缓存键
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(target.getClass().getSimpleName());
        keyBuilder.append("-");
        keyBuilder.append(method.getName());
        for (Object param : params) {
            keyBuilder.append("-");
            keyBuilder.append(param.toString());
        }
        return keyBuilder.toString();
    }
}