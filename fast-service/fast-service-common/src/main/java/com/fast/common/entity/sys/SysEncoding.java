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
* 编码
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("sys_encoding")
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysEncoding extends BaseVersionEntity {
	/**
	 * id
	 */
	@TableId
	@Display("id")
	private String id;

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

}