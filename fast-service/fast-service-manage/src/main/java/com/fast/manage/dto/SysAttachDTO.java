package com.fast.manage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统附件
 *
 * @see com.fast.manage.entity.SysAttach
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysAttachDTO {

	/**
	 * id
	 */
	@Display("id")
	private String id;

	/**
	 * 附件CODE
	 */
	@Display("附件CODE")
	private String attachCode;

	/**
	 * 附件唯一标识
	 */
	@Display("附件唯一标识")
	private String attachUuid;

	/**
	 * 附件名
	 */
	@Display("附件名")
	private String attachName;

	/**
	 * 附件别名(持久化名)
	 */
	@Display("附件别名(持久化名)")
	private String attachAlias;

	/**
	 * 附件类型
	 */
	@Display("附件类型")
	private String attachSuffix;

	/**
	 * 附件名URL
	 */
	@Display("附件名URL")
	private String attachUrl;

	/**
	 * 附件存放地址
	 */
	@Display("附件存放地址")
	private String attachAddress;

	/**
	 * 附件大小
	 */
	@Display("附件大小")
	private String attachSize;


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


}