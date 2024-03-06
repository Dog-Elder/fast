package com.fast.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 值集列表
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @see com.fast.common.entity.sys.SysSetValue
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysSetValueDTO {

    /**
     * id
     */
    @Display("id")
    private String id;

    /**
     * 值集编码
     */
    @Display("值集编码")
    private String setCode;

    /**
     * 值集名称
     */
    @Display("值集名称")
    private String setValueKey;

    /**
     * 值集含义
     */
    @Display("值集含义")
    private String setValueValue;

    /**
     * 关联值集名称
     */
    @Display("关联值集名称")
    private String setRelationKey;

    /**
     * 操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作）
     */
    @Display("操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作）")
    private String setOperate;

    /**
     * 权重
     */
    @Display("权重")
    private Integer setOrder;


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