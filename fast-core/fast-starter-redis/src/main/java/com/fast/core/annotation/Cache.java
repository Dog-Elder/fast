package com.fast.core.annotation;

import com.fast.core.cache.MethodKeyGenerator;
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

    /**
     * 可选方法key生成器
     * 最好是高频不重要的数据可采用这种方式,因为生成的逻辑稍微复杂导致不好删除
     * 一定要增加过期时间
     *
     * @see MethodKeyGenerator
     **/
    String keyGenerator() default "";

    String cacheManager() default "cacheManager";

    String cacheResolver() default "";

    String condition() default "";

    String unless() default "";

    boolean sync() default false;
}
