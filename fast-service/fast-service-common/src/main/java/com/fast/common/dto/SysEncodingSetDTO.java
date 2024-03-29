package com.fast.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 编码集
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @see com.fast.common.entity.sys.SysEncodingSet
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysEncodingSetDTO {

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
     * 状态 Y :启用 N:停用
     */
    @Display("状态 Y :启用 N:停用 ")
    private String sysEncodingSetStatus;

    /**
     * 是否已经使用 Y :已使用 N:未使用
     */
    @Display("是否已经使用 Y :已使用 N:未使用 ")
    private String sysEncodingSetUseStatus;


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