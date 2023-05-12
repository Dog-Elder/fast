package com.fast.core.common.validate.annotation;

import com.fast.core.common.validate.FileNotNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 验证MultiPartFile是否为空
 */
@Constraint(validatedBy = FileNotNullValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileNotNull {
    String message() default "上传文件不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
