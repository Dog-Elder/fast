package com.fast.manage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import java.io.Serializable;

/**
 * 
 *
 * @see com.fast.manage.entity.SysTest
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysTestDTO {

	private Integer id;

	private String code;

	private String name;

	private Integer type;


}