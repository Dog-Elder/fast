package com.fast.core.common.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 上下文持有人
 *
 * @author 黄嘉浩
 * @date 2023/07/24
 */
public class ContextHolder {
    /**
     * 上下文持
     */
    private static final Map<Class<?>, ThreadLocal<?>> CONTEXT_HOLDER = new ConcurrentHashMap<>();

    /**
     * 放入
     *
     * @param key   关键
     * @param value 价值
     */
    public static <T> void put(Class<T> key, T value) {
        ThreadLocal<T> threadLocal = getContextThreadLocal(key);
        threadLocal.set(value);
    }

    /**
     * 获取
     *
     * @param key 关键
     * @return {@link T}
     */
    public static <T> T get(Class<T> key) {
        ThreadLocal<T> threadLocal = getContextThreadLocal(key);
        return threadLocal.get();
    }

    /**
     * 删除
     *
     * @param key 关键
     */
    public static <T> void remove(Class<T> key) {
        ThreadLocal<T> threadLocal = getContextThreadLocal(key);
        threadLocal.remove();
    }

    /**
     * 清除
     */
    public static <T> void clear() {
        CONTEXT_HOLDER.clear();
    }

    /**
     * 获取线程本地上下文
     *
     * @param key 关键
     * @return {@link ThreadLocal}<{@link T}>
     */
    private static <T> ThreadLocal<T> getContextThreadLocal(Class<T> key) {
        ThreadLocal<?> threadLocal = CONTEXT_HOLDER.get(key);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<>();
            CONTEXT_HOLDER.put(key, threadLocal);
        }
        return (ThreadLocal<T>) threadLocal;
    }
}