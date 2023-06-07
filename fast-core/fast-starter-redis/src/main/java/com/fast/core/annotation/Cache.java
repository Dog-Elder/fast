package com.fast.core.annotation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 黄嘉浩
 */
@Cacheable
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cache {
    /**
     * 不设置代表永久
     * 过期时间在最后使用[@ttl=30]秒
     * 例如 user:test@ttl=30
     **/
    @AliasFor(value = "cacheNames")
    String[] value() default {};

    @AliasFor(value = "value")
    String[] cacheNames() default {};

    String key() default "";

    String keyGenerator() default "methodKeyGenerator";

    String cacheManager() default "";

    String cacheResolver() default "";

    String condition() default "";

    String unless() default "";

    boolean sync() default false;
}
