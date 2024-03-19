package com.fast.common.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
* 编码查询
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysEncodingQuery extends Query {

    /**
     * 规则代码
     */
    @Display("规则代码")
    @TableField("sys_encoding_code")
    private String sysEncodingCode;

    /**
     * 规则名称
     */
    @Display("规则名称")
    @TableField("sys_encoding_name")
    private String sysEncodingName;

    /**
     * 备注信息
     */
    @Display("备注信息")
    @TableField("remark")
    private String remark;

}