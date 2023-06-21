package com.fast.manage.config.security.authentication;

import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import com.fast.core.safe.entity.Authentication;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: fast
 * @description: 账号密码认证
 * @author: 黄嘉浩
 * @create: 2023-06-03 16:53
 **/
@Data
public class UserPasswordAuthentication extends Authentication {

    /**
     * 用户名/员工编号
     */
    @Display("用户名/员工编号")
    @NotBlank(message = Com.Require)
    private String username;

    /**
     * 密码
     */
    @Display("密码")
    @NotBlank(message = Com.Require)
    private String password;

    /**
     * 图片验证码
     **/
    private String imageVerificationCode;
}
