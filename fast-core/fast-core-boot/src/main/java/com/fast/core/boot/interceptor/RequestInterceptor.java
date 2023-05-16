package com.fast.core.boot.interceptor;

import com.fast.core.common.util.SUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * 请求拦截器
 **/
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 打印请求路径和请求体
        StringBuilder beforeReqLog = new StringBuilder();
        List<Object> beforeReqArgs = new ArrayList<>();
        beforeReqLog.append("===================接口请求拦截 Start=================== \n");
        beforeReqLog.append("===>请求方式 {}: 请求路径{}\n");
        beforeReqArgs.add(request.getMethod());
        beforeReqArgs.add(request.getRequestURI());
        if (SUtil.isNotBlank(request.getQueryString())) {
            beforeReqLog.append("===>请求参数 {}\n");
            beforeReqArgs.add(request.getQueryString());
        }
        log.info(beforeReqLog.toString(),beforeReqArgs.toArray());
//        log.info("请求路径：" + request.getRequestURI());
//        log.info("请求体：" + request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            log.info("请求方法：" + handlerMethod);
//        }


        // 获取所有请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String paramName : parameterMap.keySet()) {
            String[] paramValues = parameterMap.get(paramName);
            log.info("参数：" + paramName + " = " + Arrays.toString(paramValues));
        }

        // 获取Headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("Header：" + headerName + " = " + headerValue);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        int status = response.getStatus();
        String responseBody = getResponseBody(response);

        // 打印响应结果
        System.out.println("响应状态码: " + status);
        System.out.println("响应体: " + responseBody);
    }

    private String getResponseBody(HttpServletResponse response) {
        // 获取响应体
        // 注意: 这里的实现依赖于具体的响应输出流处理方式，你可能需要根据自己的实际情况进行适当的修改
        // 以下示例是将响应结果输出到一个临时的HttpServletResponseWrapper中，再从中获取响应体
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        return responseWrapper.getResponseContent();
    }

    private static class ResponseWrapper extends HttpServletResponseWrapper {
        private CharArrayWriter writer;

        public ResponseWrapper(HttpServletResponse response) {
            super(response);
            writer = new CharArrayWriter();
        }

        @Override
        public PrintWriter getWriter() {
            return new PrintWriter(writer);
        }

        public String getResponseContent() {
            return writer.toString();
        }
    }
}