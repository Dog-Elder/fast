package com.fast.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统消息
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @see com.fast.common.entity.SysNewsSend
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysNewsSendDTO {

    /**
     * id
     */
    @Display("id")
    private String id;

    /**
     * 发送者ID
     */
    @Display("发送者ID")
    private String sendId;

    /**
     * 标题
     */
    @Display("标题")
    private String title;

    /**
     * 概述
     */
    @Display("概述")
    private String outline;

    /**
     * 内容
     */
    @Display("内容")
    private String content;

    /**
     * 类型 对应枚举
     */
    @Display("类型 对应枚举")
    private Integer type;


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

    /**
     * 版本
     */
    @Display("版本")
    private Integer version;


}