package com.fast.core.annotation;

import com.fast.core.cache.MethodKeyGenerator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 缓存
 *
 * @author 黄嘉浩
 * @date 2023/08/18
 */
@Cacheable
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cache {
    /**
     * 价值
     * 不设置代表永久
     * 过期时间在最后使用[@ttl=30]秒
     * 例如 user:test@ttl=30
     * * @return {@link String[]}
     */
    @AliasFor(value = "cacheNames")
    String[] value() default {};

    /**
     * 缓存名称
     *
     * @return {@link String[]}
     */
    @AliasFor(value = "value")
    String[] cacheNames() default {};

    /**
     * 关键
     *
     * @return {@link String}
     */
    String key() default "";

    /**
     * 键生成器
     * 可选方法key生成器
     * 最好是高频不重要的数据可采用这种方式,因为生成的逻辑稍微复杂导致不好删除
     * 一定要增加过期时间
     *
     * @see MethodKeyGenerator
     * * @return {@link String}
     */
    String keyGenerator() default "";

    /**
     * 缓存管理器
     *
     * @return {@link String}
     */
    String cacheManager() default "cacheManager";

    /**
     * 缓存解析器
     *
     * @return {@link String}
     */
    String cacheResolver() default "";

    /**
     * 条件
     *
     * @return {@link String}
     */
    String condition() default "";

    /**
     * 除非
     *
     * @return {@link String}
     */
    String unless() default "";

    /**
     * 同步
     *
     * @return boolean
     */
    boolean sync() default false;
}
