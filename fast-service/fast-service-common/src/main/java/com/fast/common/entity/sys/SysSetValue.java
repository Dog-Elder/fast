package com.fast.common.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.common.entity.verification.Qry;
import com.fast.common.entity.verification.Save;
import com.fast.common.entity.verification.Update;
import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import com.fast.core.mybatis.model.BaseVersionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 值集值对象 sys_set_value
 *
 * @author @Dog_Elder
 * @date 2022-03-24
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class SysSetValue extends BaseVersionEntity {
    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @Display("id")
    @NotNull(message = Com.Require, groups = {Update.class})
    private Long id;

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

}
