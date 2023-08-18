package com.fast.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统参数配置
 *
 * @see com.fast.common.entity.SysConfig
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-08-10
 */
@Data
public class SysConfigVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Display("id")
	private String id;

	/**
	 * 参数名
	 */
	@Display("参数名")
	private String paramName;

	/**
	 * 参数键
	 */
	@Display("参数键")
	private String paramKey;

	/**
	 * 参数值
	 */
	@Display("参数值")
	private String paramValue;

	/**
	 * 状态（1代表启用 0代表关闭）
	 */
	@Display("状态（1代表启用 0代表关闭）")
	private String state;


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
	 * 版本
	 */
	@Display("版本")
	private Integer version;

	/**
	 * 备注信息
	 */
	@Display("备注信息")
	private String remark;

	/**
	 * 操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作）
	 */
	@Display("操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作）")
	private String operate;


}