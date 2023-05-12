package com.fast.service.vo;

import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 自定义设置值VO
 **/
@Data
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class CustomSetValueVO {

    @Display("值集编码")
    private String setCode;

    @Display("值集名称")
    private String setValueKey;

    @Display("值集含义")
    private String setValueValue;

    @Display("关联值集Key")
    private String setRelationKey;

    @Display("关联值集含义")
    private String setRelationValue;

    @Display("权重")
    private BigDecimal setOrder;
}
