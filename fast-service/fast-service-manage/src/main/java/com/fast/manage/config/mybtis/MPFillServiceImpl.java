package com.fast.manage.config.mybtis;

import com.fast.core.common.util.Util;
import com.fast.core.mybatis.annotation.AutoFill;
import com.fast.core.mybatis.config.MyMetaObjectHandler;
import com.fast.core.mybatis.service.MPFillService;
import com.fast.core.safe.util.ManageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 数据处理
 *
 * @Author: 黄嘉浩
 * @Date: 2023-06-06 14:53
 **/
@Slf4j
@Primary
@Component
public class MPFillServiceImpl implements MPFillService {
    @Override
    public void insertFill(MyMetaObjectHandler handler, MetaObject metaObject) {
        defaultInsertFill(handler, metaObject);
        AutoFill autoFill = getAutoFill(metaObject);
        if (Util.isNull(autoFill) || autoFill.allClose()) {
            return;
        }
        try {
            if (autoFill.createBy()) {
                // 特殊实现逻辑
                handler.setFieldValByName("createBy", ManageUtil.getLoginId(), metaObject);
            }
            if (autoFill.updateBy()) {
                // 特殊实现逻辑
                handler.setFieldValByName("updateBy", ManageUtil.getLoginId(), metaObject);
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public void updateFill(MyMetaObjectHandler handler, MetaObject metaObject) {
        defaultUpdateFill(handler, metaObject);
        AutoFill autoFill = getAutoFill(metaObject);
        if (Util.isNull(autoFill) || autoFill.allClose()) {
            return;
        }
        try {
            if (autoFill.updateBy()) {
                // 特殊实现逻辑
                handler.setFieldValByName("updateBy", ManageUtil.getLoginId(), metaObject);
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }
}
