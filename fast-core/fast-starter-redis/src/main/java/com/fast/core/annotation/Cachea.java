package com.fast.core.annotation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
@Cacheable
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cachea {
    @AliasFor(value ="cacheNames")
//    @AliasFor(annotation = Cacheable.class,value ="cacheNames")
    String[] value() default {};
    @AliasFor(value = "value")
//    @AliasFor(annotation = Cacheable.class,value = "value")
    String[] cacheNames() default {};

    String key() default "";
//    @AliasFor(annotation = Cacheable.class,value = "keyGenerator")
    String keyGenerator() default "methodKeyGenerator";

    String cacheManager() default "";

    String cacheResolver() default "";

    String condition() default "";

    String unless() default "";

    boolean sync() default false;
}
