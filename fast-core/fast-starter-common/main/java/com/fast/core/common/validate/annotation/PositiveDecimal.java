package com.fast.core.common.validate.annotation;

import com.fast.core.common.validate.PositiveDecimalValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 进行有效非负小数验证，
 * canBeZero为true时包含0，false时不包含0，默认false
 * scale：最大小数位数，默认是8
 * 只适用于BigDecimal
 */
@Constraint(validatedBy = PositiveDecimalValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveDecimal {

    String message() default "请输入合理数值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * desc canBeZero为true时包含0，false时不包含0，默认false
     **/
    boolean canBeZero() default false;

    /**
     * desc 最大小数位数，默认是8
     **/
    int scale() default 8;
}


