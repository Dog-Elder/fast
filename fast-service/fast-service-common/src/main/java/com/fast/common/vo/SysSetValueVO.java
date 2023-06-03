package com.fast.common.vo;

import com.fast.common.entity.sys.SysSetValue;
import com.fast.core.common.validate.annotation.Display;
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
