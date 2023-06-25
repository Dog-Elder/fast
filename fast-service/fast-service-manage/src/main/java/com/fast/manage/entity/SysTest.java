package com.fast.manage.entity;

import com.fast.core.common.annotation.encode.Code;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import com.fast.core.common.validate.annotation.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
* 测试
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-21
*/

@Data
@TableName("sys_test")
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysTest {
	@TableId
	@Display("")
	@TableField("id")
	private Integer id;

	@Code(rules = "USER", encode = "MANAGE")
	@Display("")
	@TableField("code")
	private String code;

	@Display("")
	@TableField("name")
	private String name;

	@Display("")
	@TableField("type")
	private Integer type;

}