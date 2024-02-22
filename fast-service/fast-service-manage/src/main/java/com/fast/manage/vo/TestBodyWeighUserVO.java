package com.fast.manage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 体重记录用户
 *
 * @see com.fast.manage.entity.TestBodyWeighUser
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-02-20
 */
@Data
public class TestBodyWeighUserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Display("id")
	private String id;

	/**
	 * 名字
	 */
	@Display("名字")
	private String name;

	/**
	 * 最新体重(斤)
	 */
	@Display("最新体重(斤)")
	private BigDecimal latestBodyWeight;

	/**
	 * 历史最高体重(斤)
	 */
	@Display("历史最高体重(斤)")
	private BigDecimal supremeBodyWeight;

	/**
	 * 历史最低体重(斤)
	 */
	@Display("历史最低体重(斤)")
	private BigDecimal minimumBodyWeight;

	/**
	 * 连续打卡
	 */
	@Display("连续打卡")
	private Integer continuousRecording;


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