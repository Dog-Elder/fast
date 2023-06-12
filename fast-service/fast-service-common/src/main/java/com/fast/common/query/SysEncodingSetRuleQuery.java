package com.fast.common.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
* 编码段查询
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysEncodingSetRuleQuery extends Query {
    /**
     * id
     */
    @Display("id")
    @TableField("id")
    private String id;

    /**
     * 规则代码
     */
    @Display("规则代码")
    @TableField("sys_encoding_code")
    private String sysEncodingCode;

    /**
     * 编码值
     */
    @Display("编码值")
    @TableField("sys_encoding_set_code")
    private String sysEncodingSetCode;

    /**
     * 序号
     */
    @Display("序号")
    @TableField("sys_encoding_set_rule_number")
    private Integer sysEncodingSetRuleNumber;

}