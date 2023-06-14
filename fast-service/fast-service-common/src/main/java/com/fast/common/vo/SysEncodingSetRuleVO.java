package com.fast.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.domain.vo.Vo;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 编码段
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@Data
public class SysEncodingSetRuleVO extends Vo {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Display("id")
	private String id;

	/**
	 * 规则代码
	 */
	@Display("规则代码")
	private String sysEncodingCode;

	/**
	 * 编码值
	 */
	@Display("编码值")
	private String sysEncodingSetCode;

	/**
	 * 序号
	 */
	@Display("序号")
	private Integer sysEncodingSetRuleNumber;

	/**
	 * 规则段类型
	 */
	@Display("规则段类型")
	private String sysEncodingSetRuleType;

	/**
	 * 段值
	 */
	@Display("段值")
	private String sysEncodingSetRuleSectionCode;

	/**
	 * 日期掩码
	 */
	@Display("日期掩码")
	private String sysEncodingSetRuleDateMask;

	/**
	 * 重置频率
	 */
	@Display("重置频率")
	private String sysEncodingSetRuleResetFrequency;

	/**
	 * 位数
	 */
	@Display("位数")
	private Integer sysEncodingSetRuleDigit;

	/**
	 * 开始值
	 */
	@Display("开始值")
	private String sysEncodingSetInitialValue;

	/**
	 * 当前值
	 */
	@Display("当前值")
	private String sysEncodingSetNowValue;

	/**
	 * 上次重置时间
	 */
	@Display("上次重置时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime sysEncodingSetLastTimeResetTiem;


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
	 * 版本
	 */
	@Display("版本")
	private Integer version;

	/**
	 * 备注信息
	 */
	@Display("备注信息")
	private String remark;


}