package com.fast.core.common.validate.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Display {
    String value() default "";

    String enValue() default "";

    String trueDisplay() default "是";

    String falseDisplay() default "否";
}
