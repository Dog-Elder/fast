package com.fast.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.common.entity.verification.Qry;
import com.fast.common.entity.verification.Save;
import com.fast.common.entity.verification.Update;
import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description:
 **/
@Data
public class SysSetValueVO implements Serializable {
    /**
     * id
     */
    @Display("id")
    @NotNull(message = Com.Require, groups = {Update.class})
    private String id;

    /**
     * 值集编码
     */
    @Display("值集编码")
    @TableField("`set_code`")
    @NotBlank(message = Com.Require, groups = {Save.class, Qry.class})
    private String setCode;

    /**
     * 值集值key
     */
    @Display("值集值key")
    @TableField("`set_value_key`")
    @NotBlank(message = Com.Require, groups = {Save.class, Update.class})
    private String setValueKey;

    /**
     * 值集含义
     */
    @Display("值集含义")
    @TableField("`set_value_value`")
    private String setValueValue;

    /**
     * 关联值集值key
     */
    @Display("关联值集值key")
    @TableField("`set_relation_key`")
    private String setRelationKey;


    /**
     * 关联值集名称对应的值集含义
     */
    @Display("关联值集含义")
    private String setRelationValue;

    /**
     * 操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作）
     */
    @Display("操作权限")
    @TableField("`set_operate`")
    private String setOperate;

    /**
     * 权重
     */
    @Display("权重")
    @TableField("`set_order`")
    @Digits(integer = 4, fraction = Com.MoneyScale, message = "整数不能超过4位,小数不能2位", groups = {Save.class, Update.class})
    private BigDecimal setOrder;

    /**
     * 创建者
     */
    @Display("创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @Display("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @Display("更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @Display("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 版本
     */
    @Display("版本")
    private Integer version;

    /**
     * 备注信息
     */
    @Display("备注信息")
    private String remark;
}
