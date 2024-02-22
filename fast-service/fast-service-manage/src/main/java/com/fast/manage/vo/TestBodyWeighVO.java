package com.fast.manage.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.common.entity.verification.Save;
import com.fast.common.entity.verification.Update;
import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 体重记录
 *
 * @see com.fast.manage.entity.TestBodyWeigh
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-02-20
 */
@Data
public class TestBodyWeighVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Display("id")
	private String id;

	/**
	 * 体重记录用户ID
	 */
	@NotBlank(message = Com.Require, groups = {Save.class, Update.class})
	@Display("体重记录用户ID")
	private String testBodyWeighUserId;

	/**
	 * 名字
	 */
	@Display("名字")
	private String name;

	/**
	 * 体重(斤)
	 */
	@Display("体重(斤)")
	@Digits(integer = 4, fraction = Com.MoneyScale, message = "整数不能超过3位,小数不能超过2位", groups = {Save.class, Update.class})
	private BigDecimal bodyWeight;


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