package com.fast.core.common.validate.annotation;

import com.fast.core.common.validate.RateControlValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc 用于定值、定比的控制，如果该注解用在了属性A上，且指定是B属性名称，则B必须得是一个boolean类型的值，为true表示A是个比例值(限定双端验证都只能输入0到1之间的数)，为false表示A
 * 是个普通的小数值
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RateControlValidator.class)
public @interface RateControl {
    String message() default "请输入合理数值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] target();

    String[] by();

    /**
     * 如果是比例的话，可否为0
     */
    boolean[] rateCanBeZero() default true;

    /**
     * 如果是普通小数的话，可否为0
     */
    boolean[] decimalCanBeZero() default true;
}
