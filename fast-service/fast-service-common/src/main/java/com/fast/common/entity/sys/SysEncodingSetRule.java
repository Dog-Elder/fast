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

import java.time.LocalDateTime;

/**
* 编码段
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("sys_encoding_set_rule")
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysEncodingSetRule extends BaseVersionEntity {
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
	 * 编码值
	 */
	@Display("编码值")
	@TableField("sys_encoding_set_code")
	private String sysEncodingSetCode;

	/**
	 * 序号
	 */
	@Display("序号")
	@TableField("sys_encoding_set_rule_number")
	private Integer sysEncodingSetRuleNumber;

	/**
	 * 规则段类型
	 */
	@Display("规则段类型")
	@TableField("sys_encoding_set_rule_type")
	private String sysEncodingSetRuleType;

	/**
	 * 段值
	 */
	@Display("段值")
	@TableField("sys_encoding_set_rule_section_code")
	private String sysEncodingSetRuleSectionCode;

	/**
	 * 日期掩码
	 */
	@Display("日期掩码")
	@TableField("sys_encoding_set_rule_date_mask")
	private String sysEncodingSetRuleDateMask;

	/**
	 * 重置频率
	 */
	@Display("重置频率")
	@TableField("sys_encoding_set_rule_reset_frequency")
	private String sysEncodingSetRuleResetFrequency;

	/**
	 * 位数
	 */
	@Display("位数")
	@TableField("sys_encoding_set_rule_digit")
	private Integer sysEncodingSetRuleDigit;

	/**
	 * 开始值
	 */
	@Display("开始值")
	@TableField("sys_encoding_set_initial_value")
	private Long sysEncodingSetInitialValue;

	/**
	 * 当前值
	 */
	@Display("当前值")
	@TableField("sys_encoding_set_now_value")
	private Long sysEncodingSetNowValue;

	/**
	 * 上次重置时间
	 */
	@Display("上次重置时间")
	@TableField("sys_encoding_set_last_time_reset_tiem")
	private LocalDateTime sysEncodingSetLastTimeResetTiem;

}