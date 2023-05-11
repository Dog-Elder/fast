package com.fast.core.mybatis.base.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.fast.core.mybatis.base.annotation.AutoFill;
import com.fast.core.mybatis.base.service.MybatisFillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: xxxxx
 * @description: MyBatis-plus 创建时间/更新时间
 * @author: @Dog_Elder
 * @create: 2021-03-22 14:29
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Value(value = "${fast.apply-name}")
    private String APPLY_NAME;

    private Set<MybatisFillService> fillService = new HashSet<>();


    public void inject(MybatisFillService service) {
        fillService.add(service);
    }

    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        for (MybatisFillService service : fillService) {
            if (service.getApplyName().equals(APPLY_NAME)) {
                service.insertFill(this, metaObject);
            }
        }
//        AutoFill autoFill = getAutoFill(metaObject);
//        if (Util.isNull(autoFill) || autoFill.allClose()) {
//            return;
//        }
//        if (autoFill.createBy()) {
//
//        }
//        if (autoFill.createTime()) {
//            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
//        }
//        if (autoFill.updateBy()) {
//
//        }
//        if (autoFill.updateTime()) {
//            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
//        }
//        if (autoFill.defaultVersion()) {
//            this.setFieldValByName("version", 1, metaObject);
//        }
    }

    private AutoFill getAutoFill(MetaObject metaObject) {
        //获取运行类
        Class<?> performClass = metaObject.getOriginalObject().getClass();
        //获取类标注
        return performClass.getAnnotation(AutoFill.class);
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        for (MybatisFillService service : fillService) {
            if (service.getApplyName().equals(APPLY_NAME)) {
                service.updateFill(this, metaObject);
            }
        }
//        AutoFill autoFill = getAutoFill(metaObject);
//        if (Util.isNull(autoFill) || autoFill.allClose()) {
//            return;
//        }
//        if (autoFill.updateBy()) {
//
//        }
//        if (autoFill.updateTime()) {
//            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
//        }
    }
}
