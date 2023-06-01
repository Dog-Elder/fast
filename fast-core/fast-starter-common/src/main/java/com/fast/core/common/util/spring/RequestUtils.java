package com.fast.core.common.util.spring;

import javax.servlet.http.HttpServletRequest;
/**
 * 请求类型判断工具类
 **/
public class RequestUtils {

    /**
     * 判断请求是否为JSON数据类型
     *
     * @param request HTTP请求对象
     * @return true表示请求为JSON数据类型，false表示不是JSON数据类型
     */
    public static boolean isJsonContentType(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.contains("application/json");
    }

    /**
     * 判断请求是否为表单URL编码数据类型 application/x-www-form-urlencoded
     *
     * 数据格式：这种格式将数据编码为键值对的形式，使用key=value的形式进行编码，多个键值对之间使用&进行分隔。
     * 用途：常用于传输简单的表单数据，例如登录表单、搜索表单等。
     * 示例：name=John+Doe&age=25&city=New+York
     * @param request HTTP请求对象
     * @return true表示请求为表单URL编码数据类型，false表示不是表单URL编码数据类型
     */
    public static boolean isFormUrlEncodedContentType(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.contains("application/x-www-form-urlencoded");
    }

    /**
     * 判断请求是否为多部分表单数据类型 multipart/form-data
     *
     * 数据格式：这种格式适用于上传文件或包含二进制数据的表单。它将表单数据和文件数据组合在一起，并使用多个部分进行分隔。
     * 用途：常用于文件上传功能，可以同时传输表单字段和文件数据。
     *
     * @param request HTTP请求对象
     * @return true表示请求为多部分表单数据类型，false表示不是多部分表单数据类型
     */
    public static boolean isMultipartFormDataContentType(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.contains("multipart/form-data");
    }
}