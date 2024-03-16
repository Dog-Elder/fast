package com.fast.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.core.common.validate.annotation.Display;
import com.fast.core.mybatis.model.BaseVersionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
* 系统参数配置
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-08-10
*/
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("sys_config")
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysConfig extends BaseVersionEntity {

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
	 * 参数值
	 */
	@Display("参数值")
	@TableField("param_value")
	private String paramValue;

	/**
	 * 状态（1代表启用 0代表关闭）
	 */
	@Display("状态（1代表启用 0代表关闭）")
	@TableField("state")
	private String state;

	/**
	 * 操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作）
	 */
	@Display("操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作）")
	@TableField("operate")
	private String operate;

}