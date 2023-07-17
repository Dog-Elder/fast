package com.fast.manage.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.common.entity.verification.Save;
import com.fast.common.entity.verification.Update;
import com.fast.core.common.annotation.lov.Lov;
import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 后台用户
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-20
 */
@Data
public class SysUserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Display("id")
    @NotBlank(message = Com.Require, groups = {Update.class})
    @Null(message = Com.NotRequire, groups = {Save.class})
    private String id;

    /**
     * 用户编码
     */
    @Display("用户编码")
    @Null(message = Com.NotRequire, groups = {Update.class})
    private String code;

    /**
     * 用户名
     */
    @Display("用户名")
    @NotBlank(message = Com.Require, groups = {Save.class})
    private String username;

    /**
     * 密码
     */
    @Display("密码")
    @NotBlank(message = Com.Require, groups = {Save.class})
    private String password;

    /**
     * 昵称
     */
    @Display("昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @Display("邮箱")
    private String email;

    /**
     * 姓名
     */
    @Display("姓名")
    private String realName;

    /**
     * 性别
     */
    @Display("性别")
    private Integer sex;

    /**
     * 手机号
     */
    @Display("手机号")
    private String phoneNumber;

    /**
     * 头像URL
     */
    @Display("头像URL")
    private String headPortrait;

    /**
     * 管理员 1 : 是
     */
    @Display("管理员 1 : 是")
    @Null(message = Com.NotRequire, groups = {Save.class, Update.class})
    @Lov(value = "STATUS")
    private Integer administrator;

    /**
     * 状态 1 :正常 0:停封
     */
    @Display("状态 1 :正常 0:停封 ")
    @Lov(value = "STATUS")
    private Integer status;


    /**
     * 最近操作
     */
    @Display("最近操作")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recentOperation;

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
    @NotNull(message = Com.Require, groups = Update.class)
    @Null(message = Com.NotRequire, groups = Save.class)
    private Integer version;


}