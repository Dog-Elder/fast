package com.fast.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.core.common.validate.annotation.Display;
import com.fast.core.mybatis.model.BaseVersionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户认证管理
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-08-22
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("sys_user_authentication")
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysUserAuthentication extends BaseVersionEntity {

    /**
     * 用户编码
     */
    @Display("用户编码")
    @TableField("user_code")
    private String userCode;

    /**
     * 用户名
     */
    @Display("用户名")
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @Display("密码")
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 存储邮箱
     */
    @Display("存储邮箱")
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @Display("手机号")
    @TableField("phone")
    private String phone;


}