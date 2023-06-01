package com.fast.core.boot.interceptor.servlet;

import com.fast.core.common.util.spring.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;

/**
 * request.getInputStream()输入流只能读取一次问题
 *
 * @summary 自定义 HttpServletRequestWrapper 来包装输入流
 */
@Slf4j
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

    private byte[] body;

    public HttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        //因为请求 内容类型 可能不同所以需要根据不同的 内容类型 进行缓存
        if (RequestUtils.isJsonContentType(request)) {
            body = StreamUtils.copyToByteArray(request.getInputStream());
        } else {
            cacheParameters(request);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        InputStream bodyStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bodyStream.read();
            }

            /**
             * 下面的方法一般情况下不会被使用，如果你引入了一些需要使用ServletInputStream的外部组件，可以重点关注一下。
             * @return
             */
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 缓存 multipart/form-data
     **/
    void cacheParameters(HttpServletRequest request) throws IOException {
        // 可以选择缓存请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        String queryString = request.getQueryString();
        // 将请求参数转换为字节数组
        String parameterString = "";
        if (queryString != null) {
            parameterString = queryString;
        } else {
            for (String parameterName : parameterMap.keySet()) {
                String[] parameterValues = parameterMap.get(parameterName);
                for (String parameterValue : parameterValues) {
                    parameterString += parameterName + "=" + parameterValue + "&";
                }
            }
            if (!parameterString.isEmpty()) {
                parameterString = parameterString.substring(0, parameterString.length() - 1);
            }
        }
        body = StreamUtils.copyToByteArray(new ByteArrayInputStream(parameterString.getBytes()));
    }


}