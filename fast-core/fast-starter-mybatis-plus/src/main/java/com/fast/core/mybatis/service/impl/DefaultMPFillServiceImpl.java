package com.fast.core.mybatis.service.impl;

import com.fast.core.common.util.Util;
import com.fast.core.mybatis.annotation.AutoFill;
import com.fast.core.mybatis.config.MyMetaObjectHandler;
import com.fast.core.mybatis.service.MPFillService;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @description: MP 填充服务 默认实现
 * @author: 黄嘉浩
 * @create: 2023-06-03 00:14
 **/
@Component
public class DefaultMPFillServiceImpl implements MPFillService {

    @Override
    public void insertFill(MyMetaObjectHandler handler, MetaObject metaObject) {
        AutoFill autoFill = getAutoFill(metaObject);
        if (Util.isNull(autoFill) || autoFill.allClose()) {
            return;
        }
        if (autoFill.createBy()) {
            //特殊实现逻辑
        }
        if (autoFill.createTime()) {
            handler.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
        if (autoFill.updateBy()) {
            //特殊实现逻辑
        }
        if (autoFill.updateTime()) {
            handler.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
        if (autoFill.defaultVersion()) {
            handler.setFieldValByName("version", 1, metaObject);
        }
    }

    @Override
    public void updateFill(MyMetaObjectHandler handler, MetaObject metaObject) {
        AutoFill autoFill = getAutoFill(metaObject);
        if (Util.isNull(autoFill) || autoFill.allClose()) {
            return;
        }
        if (autoFill.updateBy()) {
            //特殊实现逻辑
        }
        if (autoFill.updateTime()) {
            handler.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
