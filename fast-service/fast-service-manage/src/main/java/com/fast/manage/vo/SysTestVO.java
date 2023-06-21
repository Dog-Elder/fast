package com.fast.manage.vo;

import com.fast.core.common.annotation.encode.Code;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import java.io.Serializable;

/**
* 测试
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-21
*/
@Data
public class SysTestVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@Code("test")
	private String code;

	private String name;

	private Integer type;


}