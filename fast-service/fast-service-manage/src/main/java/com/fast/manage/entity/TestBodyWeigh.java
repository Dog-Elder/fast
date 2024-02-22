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
* 体重记录
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2024-02-20
*/
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("test_body_weigh")
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class TestBodyWeigh extends BaseEntity {
	/**
	 * id
	 */
	@TableId
	@Display("id")
	@TableField("id")
	private String id;

	/**
	 * 体重记录用户ID
	 */
	@Display("体重记录用户ID")
	@TableField("test_body_weigh_user_id")
	private String testBodyWeighUserId;

	/**
	 * 体重(斤)
	 */
	@Display("体重(斤)")
	@TableField("body_weight")
	private BigDecimal bodyWeight;

}