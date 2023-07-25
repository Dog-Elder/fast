package com.fast.core.boot.interceptor;

import com.fast.core.common.context.ContextHolder;
import com.fast.core.log.model.RequestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 响应处理
 **/
@Slf4j
@ControllerAdvice
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {
    private final ObjectMapper objectMapper;

    public ResponseInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //  指定拦截的响应类型，可以根据需要进行调整
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 组装请求模型
        RequestContext context = Optional.of(ContextHolder.get(RequestContext.class)).orElse(new RequestContext());

        StringBuilder afterReqLog = new StringBuilder();
        List<Object> afterReqArgs = new ArrayList<>();
        afterReqLog.append("\n");
        afterReqLog.append("===================响应id:" + context.getRequestId() + " Start=================== \n");
        afterReqLog.append("\n");
        try {
            String responseBody = objectMapper.writeValueAsString(body);
            afterReqLog.append("接口响应参数 {} \n");
            afterReqArgs.add(responseBody);

            context.setResponderBody(responseBody);
        } catch (Exception e) {
            afterReqLog.append("转换响应参数失败:{}");
            afterReqArgs.add(e);
        }
        afterReqLog.append("\n");
        afterReqLog.append("===================响应:" + context.getRequestId() + " End=================== \n");
        log.info(afterReqLog.toString(), afterReqArgs.toArray());
        return body;
    }
}
