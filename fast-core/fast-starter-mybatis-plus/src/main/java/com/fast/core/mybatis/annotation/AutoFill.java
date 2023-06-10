package com.fast.core.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 公共字段值自动装配开关
 * 执行MP增删时会自动会会维护公共字段
 *
 *
 * @author 黄嘉浩*/
@Inherited
@Target({ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AutoFill {
    /**
     * allClose为true时以下值不会默认填充
     * createTime()
     * createBy()
     * updateTime()
     * updateBy()
     **/
    boolean allClose() default false;

    /**
     * 创建时间
     **/
    boolean createTime() default true;

    /**
     * 创建人
     **/
    boolean createBy() default true;

    /**
     * 修改时间
     **/
    boolean updateTime() default true;

    /**
     * 修改人
     **/
    boolean updateBy() default true;

    /**
     * 使用mysql乐观锁默认不开启
     **/
    boolean defaultVersion() default false;
}
