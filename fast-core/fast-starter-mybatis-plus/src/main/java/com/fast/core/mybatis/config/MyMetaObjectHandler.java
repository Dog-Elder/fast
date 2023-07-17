package com.fast.core.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.fast.core.mybatis.annotation.AutoFill;
import com.fast.core.mybatis.service.MPFillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @program: xxxxx
 * @description: MyBatis-plus 创建时间/更新时间
 * @author: 黄嘉浩
 * @create: 2021-03-22 14:29
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private MPFillService service;

    public MyMetaObjectHandler(MPFillService fillService) {
        this.service = fillService;
    }

    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        service.insertFill(this, metaObject);
    }

    private AutoFill getAutoFill(MetaObject metaObject) {
        // 获取运行类
        Class<?> performClass = metaObject.getOriginalObject().getClass();
        // 获取类标注
        return performClass.getAnnotation(AutoFill.class);
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        service.updateFill(this, metaObject);
    }
}
