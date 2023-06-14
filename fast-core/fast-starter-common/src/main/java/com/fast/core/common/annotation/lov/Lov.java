package com.fast.core.common.annotation.lov;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 继承Vo可自动翻译
 * @see com.fast.core.common.domain.vo.Vo
 * @author 黄嘉浩
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lov {
    /**
     * 值集code
     **/
    String setCode() default "";

    /**
     * 翻译到那个字段
     */
    String decipherField() default "";
}
