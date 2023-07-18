package com.fast.core.boot.interceptor;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.spring.RequestUtils;
import com.fast.core.log.model.RequestContext;
import com.fast.core.log.util.RequestContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 接口请求拦截器
 **/
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public RequestInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 组装请求模型
        RequestContext context = RequestContextHolder.getContext();

        //  打印请求路径和请求体
        StringBuilder beforeReqLog = new StringBuilder();
        List<Object> beforeReqArgs = new ArrayList<>();
        beforeReqLog.append("\n");
        beforeReqLog.append("===================请求id:" + context.getRequestId() + " Start=================== \n");
        beforeReqLog.append("\n");
        beforeReqLog.append("===>请求方式 {}: 请求路径{}\n");
        beforeReqArgs.add(request.getMethod());
        beforeReqArgs.add(request.getRequestURI());

        // 组装请求模型
        context.setRequestManner(request.getMethod());
        context.setRequestPath(request.getRequestURI());

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            beforeReqLog.append("===>请求方法 {}\n");
            beforeReqArgs.add(handlerMethod);

            context.setRequestMethod(handlerMethod.toString());
        }

        if (SUtil.isNotBlank(request.getQueryString())) {
            beforeReqLog.append("===>请求参数 {}\n");
            beforeReqArgs.add(request.getQueryString());

            context.setRequestParameters(request.getQueryString());
        }

        //  获取RequestBody中的数据
        if (RequestUtils.isJsonContentType(request)) {
            // 获取请求body
            byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
            String body = new String(bodyBytes, request.getCharacterEncoding());
            if (SUtil.isNotBlank(body)) {
                try {
                    String formattedJson;
                    if (JSONUtil.isTypeJSONObject(body)) {
                        JSONObject jsonObject = JSONUtil.parseObj(body);
                        formattedJson = jsonObject.toJSONString(0);
                    } else {
                        JSONArray jsonArray = JSONUtil.parseArray(body);
                        formattedJson = jsonArray.toJSONString(0);
                    }
                    beforeReqLog.append("===>请求体 {}\n");
                    beforeReqArgs.add(formattedJson);

                    context.setRequestBody(formattedJson);
                } catch (Exception e) {
                    beforeReqLog.append("===>请求体解析失败，原因: {}\n");
                    beforeReqArgs.add(e.getMessage());
                }
            }
        }

        //  获取所有请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String paramName : parameterMap.keySet()) {
            String[] paramValues = parameterMap.get(paramName);
            beforeReqLog.append("===>请求参数 {}\n");
            beforeReqArgs.add(paramName + " = " + Arrays.toString(paramValues));
        }
        beforeReqLog.append("\n");

        //  获取Headers
        Enumeration<String> headerNames = request.getHeaderNames();
        JSONObject headerJson = JSONUtil.createObj();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            beforeReqLog.append("===>Header {}\n");
            beforeReqArgs.add(headerName + " = " + headerValue);
            headerJson.putOpt(headerName, headerValue);
        }

        context.setRequestHeaders(headerJson.toString());
        context.setRequestHeaderJson(headerJson);

        beforeReqLog.append("\n");
        beforeReqLog.append("===================请求id:" + context.getRequestId() + " End=================== \n");
        log.info(beforeReqLog.toString(), beforeReqArgs.toArray());
        return true;
    }


}
