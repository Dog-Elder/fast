package com.fast.core.common.validate.annotation;

import com.fast.core.common.validate.PositiveMoneyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 进行有效金额验证：正数，且小数位数不超过2位的数字，才是有效金额，
 * canBeZero为true时包含0，false时不包含0，默认false
 * 只针对BigDecimal
 */
@Constraint(validatedBy = PositiveMoneyValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveMoney {

    String message() default "请输入合理金额";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean canBeZero() default false;
}
