package com.fast.common.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
* 值集查询
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysSetQuery extends Query {
    /**
     * id
     */
    @Display("id")
    @TableField("id")
    private String id;

    /**
     * 值集编码
     */
    @Display("值集编码")
    @TableField("set_code")
    private String setCode;

    /**
     * 父值集编码
     */
    @Display("父值集编码")
    @TableField("set_parent_code")
    private String setParentCode;

    /**
     * 值集名称
     */
    @Display("值集名称")
    @TableField("set_name")
    private String setName;

    /**
     * 值集概要
     */
    @Display("值集概要")
    @TableField("set_describe")
    private String setDescribe;

    /**
     * 备注信息
     */
    @Display("备注信息")
    @TableField("remark")
    private String remark;

}