package com.fast.core.mybatis.base.service;

import com.fast.core.mybatis.base.config.MyMetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

public interface MybatisFillService {
    String getApplyName();

    void insertFill(MyMetaObjectHandler handler, MetaObject metaObject);

    void updateFill(MyMetaObjectHandler handler, MetaObject metaObject);
}