package com.fast.core.mybatis.service;

import com.fast.core.mybatis.annotation.AutoFill;
import com.fast.core.mybatis.config.MyMetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

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
        return performClass.getAnnotation(AutoFill.class);
    }

    void insertFill(MyMetaObjectHandler handler, MetaObject metaObject);

    void updateFill(MyMetaObjectHandler handler, MetaObject metaObject);
}