package com.fast.core.common.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 反射工厂
 **/
public class ReflectionCache {
    private static final ConcurrentHashMap<String, Field> fieldCache = new ConcurrentHashMap<>();

    /**
     * 获取字段
     **/
    public static Field getField(Object target, String property) throws NoSuchFieldException {
        if (target instanceof List<?>) {
            List<?> targets = (List<?>) target;
            return ReflectionCache.getField(targets.get(0).getClass(), property);
        }
        return ReflectionCache.getField(target.getClass(), property);
    }

    /**
     * 可以使用该方法走缓存
     **/
    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {

        String key = clazz.getName() + "#" + fieldName;
        Field field = fieldCache.get(key);
        if (field != null) {
            return field;
        }
        field = findField(clazz, fieldName);
        if (field != null) {
            field.setAccessible(true);
            fieldCache.put(key, field);
            return field;
        } else {
            throw new NoSuchFieldException(fieldName);
        }
    }

    /**
     * 该方法不走缓存
     **/
    private static Field findField(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                field = findField(superClass, fieldName);
            }
        }
        return field;
    }
}