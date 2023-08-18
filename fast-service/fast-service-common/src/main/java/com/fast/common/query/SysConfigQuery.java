package com.fast.common.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统参数配置查询
 *
 * @see com.fast.common.entity.SysConfig
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-08-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysConfigQuery extends Query {
    /**
     * id
     */
    @Display("id")
    @TableField("id")
    private String id;

    /**
     * 参数名
     */
    @Display("参数名")
    @TableField("param_name")
    private String paramName;

    /**
     * 参数键
     */
    @Display("参数键")
    @TableField("param_key")
    private String paramKey;

    /**
     * 状态（1代表启用 0代表关闭）
     */
    @Display("状态（1代表启用 0代表关闭）")
    @TableField("state")
    private String state;

}