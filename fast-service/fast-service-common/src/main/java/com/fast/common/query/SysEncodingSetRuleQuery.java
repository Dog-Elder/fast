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
* 编码段查询
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@Data
@Accessors(chain = true)
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
    @NotBlank(message = Com.Require,groups = {Qry.class})
    private String sysEncodingCode;

    /**
     * 编码值
     */
    @Display("编码值")
    @TableField("sys_encoding_set_code")
    @NotBlank(message = Com.Require,groups = {Qry.class})
    private String sysEncodingSetCode;

    /**
     * 规则段类型
     */
    @Display("规则段类型")
    @TableField("sys_encoding_set_rule_type")
    private String sysEncodingSetRuleType;

    /**
     * 序号
     */
    @Display("序号")
    @TableField("sys_encoding_set_rule_number")
    private Integer sysEncodingSetRuleNumber;

}