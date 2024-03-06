package com.fast.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户认证管理
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @see com.fast.common.entity.SysUserAuthentication
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysUserAuthenticationDTO {

    /**
     * id
     */
    @Display("id")
    private String id;

    /**
     * 用户编码
     */
    @Display("用户编码")
    private String userCode;

    /**
     * 用户名
     */
    @Display("用户名")
    private String username;

    /**
     * 密码
     */
    @Display("密码")
    private String passwordHash;

    /**
     * 存储邮箱
     */
    @Display("存储邮箱")
    private String email;

    /**
     * 手机号
     */
    @Display("手机号")
    private String phone;

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


}