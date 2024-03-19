package com.fast.manage.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* 系统附件查询
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-26
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAttachQuery extends Query {

    /**
     * 附件CODE
     */
    @Display("附件CODE")
    @TableField("attach_code")
    private String attachCode;

    /**
     * 附件唯一标识
     */
    @Display("附件唯一标识")
    @TableField("attach_uuid")
    private String attachUuid;

    /**
     * 附件名
     */
    @Display("附件名")
    @TableField("attach_name")
    private String attachName;

    /**
     * 附件别名(持久化名)
     */
    @Display("附件别名(持久化名)")
    @TableField("attach_alias")
    private String attachAlias;

}