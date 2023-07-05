package com.fast.core.boot.util;

import com.fast.core.boot.model.RequestContext;

/**
 * 请求上下文持有者
 *
 * @author 黄嘉浩
 * @date 2023-07-01 14:31
 **/
public class RequestContextHolder {
    private static final ThreadLocal<RequestContext> contextHolder = new ThreadLocal<>();

    public static void setContext(RequestContext requestContext) {
        contextHolder.set(requestContext);
    }

    public static RequestContext getContext() {
        return contextHolder.get();
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
