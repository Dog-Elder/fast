package com.fast.core.common.util;

import com.fast.core.common.domain.domain.R;
import com.fast.core.common.validate.annotation.Display;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;

public class WebUtil {

    /**
     * 入参验证
     **/
    public static R dealError(Object target, FieldError fieldError) {
        String message = fieldError == null ? Com.UnknownErr : fieldError.getDefaultMessage();
        if (StringUtils.isNotBlank(message) && message.contains("{display}")) {
            if (target == null) {
                return R.error(Com.UnknownErr);
            }
            try {
                String property = getProperty(fieldError.getField());
                Field field = getField(target, property);
                Display displayAnnotation = field.getDeclaredAnnotation(Display.class);
                if (displayAnnotation != null) {
                    String display = displayAnnotation.value();
                    message = StringUtils.replace(message, "{display}", display);
                } else {
                    message = StringUtils.replace(message, "{display}", property);
                }
            } catch (SecurityException e1) {
                LogUtil.err(target.getClass(), e1); //  记录日志
            }
        }

        if (Util.isIdea()) {
            String fieldDetail = fieldError.getObjectName() + "." + fieldError.getField() + "";
            String tip = "【注意：在IDEA运行才会显英文错误字段前缀】";
            return R.error((fieldDetail + " " + message + tip));
        } else {
            return R.error((message));
        }
    }

    /**
     * 针对集合 取属性字符串
     **/
    public static String getProperty(String property) {
        if (!property.contains(".")) {
            return property;
        }
        // 集合中校验出来属性名的是这样的 list[0].属性名 这样在下面获取标注必会报错
        return property.substring(property.lastIndexOf(".") + 1);
    }

    /**
     * 针对集合 获取类
     **/
    public static Field getField(Object target, String property) {
        if (target instanceof List<?> targets) {
            return RUtil.getField(targets.get(0).getClass(), property);
        }
        return RUtil.getField(target.getClass(), property);
    }


    /**
     * 获取IP地址
     *
     * @param request 请求
     * @return request发起客户端的IP地址
     */
    public static String getIP(HttpServletRequest request) {
        if (request == null) {
            return "0.0.0.0";
        }
        String xip = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";
        if (StringUtils.isNotEmpty(xFor) && !unknown.equalsIgnoreCase(xFor)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }

        xFor = xip;

        if (StringUtils.isNotEmpty(xFor) && !unknown.equalsIgnoreCase(xFor)) {
            return xFor;
        }

        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }

        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }

        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }

        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (StringUtils.isBlank(xFor) || unknown.equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }

        return xFor;

    }
}
