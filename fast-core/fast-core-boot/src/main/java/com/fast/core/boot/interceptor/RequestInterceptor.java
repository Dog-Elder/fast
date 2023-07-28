package com.fast.core.boot.interceptor;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.context.ContextHolder;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.spring.RequestUtils;
import com.fast.core.log.model.RequestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 接口请求拦截器
 **/
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 因为自定义了拦截器 导致 需要自己处理 映射不到的问题
        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            // 这里可以对404错误进行自定义处理
            response404(response);
            return false;
        }

        // 组装请求模型
        RequestContext context = Optional.ofNullable(ContextHolder.get(RequestContext.class)).orElse(new RequestContext());

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

    private static void response404(HttpServletResponse response) throws IOException {
        // 将JSON对象转换成字符串
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Resource not found");

        // 设置响应的内容类型为application/json
        response.setContentType("application/json");
        response.setCharacterEncoding(Constants.UTF8);
        // 将JSON字符串写入响应输出流
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), R.error(R.Type.NOT_FOUND));
    }

}
