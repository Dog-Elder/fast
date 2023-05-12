package com.fast.service.vo;

import com.fast.core.common.validate.annotation.Display;
import com.fast.service.entity.sys.SysSetValue;
import lombok.Data;

/**
 * @description:
 **/
@Data
public class SysSetValueVO extends SysSetValue {
    /**
     * 关联值集名称对应的值集含义
     */
    @Display("关联值集含义")
    private String setRelationValue;
}
