package com.fast.core.log.util;

import com.fast.core.log.model.RequestContext;

/**
 * 请求上下文持有者
 *
 * @author 黄嘉浩
 * @date 2023-07-01 14:31
 **/
public class RequestContextHolder {
    private static final ThreadLocal<RequestContext> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setContext(RequestContext requestContext) {
        CONTEXT_HOLDER.set(requestContext);
    }

    public static RequestContext getContext() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }
}
