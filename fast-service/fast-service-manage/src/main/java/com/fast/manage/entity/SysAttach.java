package com.fast.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
* 系统附件
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-26
*/
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("sys_attach")
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysAttach extends BaseVersionEntity {

	/**
	 * id
	 */
	@TableId
	@Display("id")
	private String id;

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

	/**
	 * 附件类型
	 */
	@Display("附件类型")
	@TableField("attach_suffix")
	private String attachSuffix;

	/**
	 * 附件名URL
	 */
	@Display("附件名URL")
	@TableField("attach_url")
	private String attachUrl;

	/**
	 * 附件存放地址
	 */
	@Display("附件存放地址")
	@TableField("attach_address")
	private String attachAddress;

	/**
	 * 附件大小
	 */
	@Display("附件大小")
	@TableField("attach_size")
	private String attachSize;

}