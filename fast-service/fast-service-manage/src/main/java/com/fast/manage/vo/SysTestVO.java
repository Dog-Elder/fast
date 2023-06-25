package com.fast.manage.vo;

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

	private String code;

	private String name;

	private Integer type;


}