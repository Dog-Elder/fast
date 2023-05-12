package com.fast.core.common.validate.annotation;

import com.fast.core.common.validate.FileSizeLimitValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 默认2M的限制，"50K"则表示50KB的限制，单位只支持M和K，不支持小数
 */
@Constraint(validatedBy = FileSizeLimitValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileSizeLimit {

    /**
     * 默认4M的限制，"50K"则表示50KB的限制，单位只支持M和K，不支持小数
     */
    String value() default "2M";

    String message() default "{display}大小不能超过{value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
