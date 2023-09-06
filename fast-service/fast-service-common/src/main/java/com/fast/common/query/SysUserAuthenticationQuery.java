package com.fast.common.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户认证管理查询
 *
 * @see com.fast.common.entity.SysUserAuthentication
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-08-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysUserAuthenticationQuery extends Query {
    /**
     * id
     */
    @Display("id")
    @TableField("id")
    private String id;

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