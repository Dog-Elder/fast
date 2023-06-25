package com.fast.core.common.annotation.encode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 落库可自动生成编码
 * @author 黄嘉浩
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Code {

    /**
     * 规则code
     **/
    String rules();

    /**
     * 编码值
     */
    String encode();

}
