package com.fast.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 编码
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @see com.fast.common.entity.sys.SysEncoding
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysEncodingDTO {

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
     * 规则名称
     */
    @Display("规则名称")
    private String sysEncodingName;


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