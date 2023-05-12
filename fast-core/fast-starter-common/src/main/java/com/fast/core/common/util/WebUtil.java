package com.fast.core.common.util;

import com.fast.core.common.domain.domain.R;
import com.fast.core.common.validate.annotation.Display;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;
import java.util.List;

public class WebUtil {

    public static R dealError(Object target, FieldError fieldError) {
        String message = fieldError == null ? com.fast.core.common.util.Com.UnknownErr : fieldError.getDefaultMessage();
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
                LogUtil.err(target.getClass(), e1); // 记录日志
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
        //集合中校验出来属性名的是这样的 list[0].属性名 这样在下面获取标注必会报错
        return property.substring(property.lastIndexOf(".") + 1);
    }

    /**
     * 针对集合 获取类
     **/
    public static Field getField(Object target, String property) {
        if (target instanceof List<?>) {
            List<?> targets = (List<?>) target;
            return com.fast.core.common.util.RUtil.getField(targets.get(0).getClass(), property);
        }
        return RUtil.getField(target.getClass(), property);
    }
}
