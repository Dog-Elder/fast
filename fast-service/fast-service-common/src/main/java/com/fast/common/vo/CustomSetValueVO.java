package com.fast.common.vo;

import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 自定义设置值VO
 **/
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CustomSetValueVO {
    /**
     * 值集编码
     **/
    @Display("值集编码")
    private String setCode;
    /**
     * 值集名称
     **/

    @Display("值集名称")
    private String setValueKey;
    /**
     * 值集含义
     **/
    @Display("值集含义")
    private String setValueValue;
    /**
     * 关联值集Key
     **/
    @Display("关联值集Key")
    private String setRelationKey;
    /**
     * 关联值集含义
     **/
    @Display("关联值集含义")
    private String setRelationValue;
    /**
     * 权重
     **/
    @Display("权重")
    private BigDecimal setOrder;
}
