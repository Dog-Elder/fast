package com.fast.common.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.common.entity.verification.Qry;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


/**
* 编码集查询
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysEncodingSetQuery extends Query {

    /**
     * 规则代码
     */
    @Display("规则代码")
    @TableField("sys_encoding_code")
    @NotBlank(message = Com.Require, groups = {Qry.class})
    private String sysEncodingCode;

    /**
     * 编码值
     */
    @Display("编码值")
    @TableField("sys_encoding_set_code")
    private String sysEncodingSetCode;

    /**
     * 状态 Y :启用 N:停用 
     */
    @Display("状态 Y :启用 N:停用 ")
    @TableField("sys_encoding_set_status")
    private String sysEncodingSetStatus;

    /**
     * 是否已经使用 Y :已使用 N:未使用 
     */
    @Display("是否已经使用 Y :已使用 N:未使用 ")
    @TableField("sys_encoding_set_use_status")
    private String sysEncodingSetUseStatus;

    /**
     * 备注信息
     */
    @Display("备注信息")
    @TableField("remark")
    private String remark;

}