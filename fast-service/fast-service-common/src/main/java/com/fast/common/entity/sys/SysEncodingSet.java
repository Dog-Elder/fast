package com.fast.common.entity.sys;

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
* 编码集
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("sys_encoding_set")
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysEncodingSet extends BaseVersionEntity {
	/**
	 * id
	 */
	@TableId
	@Display("id")
	@TableField("id")
	private String id;

	/**
	 * 规则代码
	 */
	@Display("规则代码")
	@TableField("sys_encoding_code")
	private String sysEncodingCode;

	/**
	 * 编码值
	 */
	@Display("编码值")
	@TableField("sys_encoding_set_code")
	private String sysEncodingSetCode;

	/**
	 * 状态 Y :启用 N:停用 
	 */
	@Display("状态 Y :启用 N:停用 ")
	@TableField("sys_encoding_set_status")
	private String sysEncodingSetStatus;

	/**
	 * 是否已经使用 Y :已使用 N:未使用 
	 */
	@Display("是否已经使用 Y :已使用 N:未使用 ")
	@TableField("sys_encoding_set_use_status")
	private String sysEncodingSetUseStatus;








}