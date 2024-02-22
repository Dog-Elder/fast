package com.fast.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.core.common.validate.annotation.Display;
import com.fast.core.mybatis.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
* 体重记录用户
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2024-02-20
*/
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("test_body_weigh_user")
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class TestBodyWeighUser extends BaseEntity {
	/**
	 * id
	 */
	@TableId
	@Display("id")
	@TableField("id")
	private String id;

	/**
	 * 名字
	 */
	@Display("名字")
	@TableField("name")
	private String name;

	/**
	 * 最新体重(斤)
	 */
	@Display("最新体重(斤)")
	@TableField("latest_body_weight")
	private BigDecimal latestBodyWeight;

	/**
	 * 历史最高体重(斤)
	 */
	@Display("历史最高体重(斤)")
	@TableField("supreme_body_weight")
	private BigDecimal supremeBodyWeight;

	/**
	 * 历史最低体重(斤)
	 */
	@Display("历史最低体重(斤)")
	@TableField("minimum_body_weight")
	private BigDecimal minimumBodyWeight;

	/**
	 * 连续打卡
	 */
	@Display("连续打卡")
	@TableField("continuous_recording")
	private Integer continuousRecording;

}