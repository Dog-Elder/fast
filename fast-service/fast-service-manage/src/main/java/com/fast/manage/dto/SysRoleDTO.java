package com.fast.manage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色
 *
 * @see com.fast.manage.entity.SysRole
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysRoleDTO {

	/**
	 * 角色ID
	 */
	@Display("角色ID")
	private String id;

	/**
	 * 角色名称
	 */
	@Display("角色名称")
	private String name;

	/**
	 * 角色权限字符串
	 */
	@Display("角色权限字符串")
	private String key;

	/**
	 * 显示顺序
	 */
	@Display("显示顺序")
	private Integer sort;

	/**
	 * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
	 */
	@Display("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
	private String dataScope;

	/**
	 * 角色状态（1正常 0停用）
	 */
	@Display("角色状态（1正常 0停用）")
	private String status;


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