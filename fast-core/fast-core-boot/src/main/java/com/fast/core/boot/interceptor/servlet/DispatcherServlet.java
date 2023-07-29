package com.fast.core.boot.interceptor.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义 DispatcherServlet 来分派 XinHttpServletRequestWrapper
 *
 * @author 黄嘉浩
 * @date 2023/07/28
 */
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {


    /**
     * 包装成我们自定义的request
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(new HttpServletRequestWrapper(request), response);
    }
}