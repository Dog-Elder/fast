package com.fast.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 值集
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @see com.fast.common.entity.sys.SysSet
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysSetDTO {

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
     * 父值集编码
     */
    @Display("父值集编码")
    private String setParentCode;

    /**
     * 值集名称
     */
    @Display("值集名称")
    private String setName;

    /**
     * 值集状态（1代表启用 0代表关闭）
     */
    @Display("值集状态（1代表启用 0代表关闭）")
    private String setState;

    /**
     * 是否分页（1代表启用 0代表关闭）
     */
    @Display("是否分页（1代表启用 0代表关闭）")
    private String setPage;

    /**
     * 值集概要
     */
    @Display("值集概要")
    private String setDescribe;


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

    /**
     * 操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作）
     */
    @Display("操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作）")
    private String setOperate;


}