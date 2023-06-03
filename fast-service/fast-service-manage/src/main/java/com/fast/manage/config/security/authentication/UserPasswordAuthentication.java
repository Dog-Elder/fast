package com.fast.manage.config.security.authentication;

import com.fast.core.safe.entity.Authentication;
import lombok.Data;

/**
 * @program: fast
 * @description: 账号密码认证
 * @author: 黄嘉浩
 * @create: 2023-06-03 16:53
 **/
@Data
public class UserPasswordAuthentication extends Authentication {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 图片验证码
     **/
    private String imageVerificationCode;
}
