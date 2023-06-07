package com.fast.core.mybatis.service;

import com.fast.core.common.util.RUtil;
import com.fast.core.common.util.Util;
import com.fast.core.mybatis.annotation.AutoFill;
import com.fast.core.mybatis.config.MyMetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * Mybatis-plus对象处理服务
 * 因为MyBatis-plus对象处理服务切入强耦 表中没有设计字段 会导致填充报错
 * 例如 创建/修改时间 创建人/修改人  从而使用 AutoFill可以进行灵活使用
 * 替换默认DefaultMPFillServiceImpl实现 在实现MPFillService接口的实现类上使用 @Primary  @Component 注解，将其标记为首选的实现，这样Spring会优先选择该实现。
 **/
public interface MPFillService {
    /**
     *获取POJO上类注解
     **/
    default AutoFill getAutoFill(MetaObject metaObject) {
        //获取运行类
        Class<?> performClass = metaObject.getOriginalObject().getClass();
        //获取类标注
        return RUtil.getAnnotation(performClass,AutoFill.class);
    }

    void insertFill(MyMetaObjectHandler handler, MetaObject metaObject);

    void updateFill(MyMetaObjectHandler handler, MetaObject metaObject);


    /**
     * 默认 插入填充
     **/
    default void defaultInsertFill(MyMetaObjectHandler handler, MetaObject metaObject) {
        AutoFill autoFill = getAutoFill(metaObject);
        if (Util.isNull(autoFill) || autoFill.allClose()) {
            return;
        }
        if (autoFill.createTime()) {
            handler.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        if (autoFill.updateTime()) {
            handler.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
        if (autoFill.defaultVersion()) {
            handler.setFieldValByName("version", 1, metaObject);
        }
    }
    /**
     * 默认 修改填充
     **/
    default void defaultUpdateFill(MyMetaObjectHandler handler, MetaObject metaObject) {
        AutoFill autoFill = getAutoFill(metaObject);
        if (Util.isNull(autoFill) || autoFill.allClose()) {
            return;
        }
        if (autoFill.updateTime()) {
            handler.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}