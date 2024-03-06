package com.fast.manage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色和菜单关联表
 *
 * @see com.fast.manage.entity.SysRoleMenu
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysRoleMenuDTO {

	/**
	 * 角色ID
	 */
	@Display("角色ID")
	private String roleId;

	/**
	 * 菜单ID
	 */
	@Display("菜单ID")
	private String menuId;

	/**
	 * 类型（0代表后台 2代表API）
	 */
	@Display("类型（0代表后台 2代表API）")
	private String type;


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
	 * 备注信息
	 */
	@Display("备注信息")
	private String remark;

	/**
	 * 版本
	 */
	@Display("版本")
	private Integer version;


}