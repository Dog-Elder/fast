package com.fast.common.service.impl;

import com.fast.common.dto.SysCreateCode;
import com.fast.common.service.ISysEncodingService;
import com.fast.core.common.annotation.encode.Code;
import com.fast.core.common.util.RUtil;
import com.fast.core.mybatis.service.IEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 黄嘉浩
 * @Date: 2023-06-25 10:58
 **/
@Slf4j
@Service
public class EventServiceImpl<T> implements IEventService<T> {
    @Lazy
    @Resource
    private ISysEncodingService sysEncodingService;
    /**
     * 填充Code
     **/
    @Override
    public void paddingCode(T entity) {
        RUtil.processFieldsWithAnnotation(entity, Code.class, (annotation) -> {
            Code autoCode = (Code) annotation;
            String rules = autoCode.rules();
            String encode = autoCode.encode();
            // 执行自定义业务逻辑，并返回对应的数据
            // 例如，根据注解中的值查询数据库，然后返回结果填充到属性中
            return sysEncodingService.createCode(new SysCreateCode()
                    .setSysEncodingCode(rules)
                    .setSysEncodingSetCode(encode));
        });
    }
}
