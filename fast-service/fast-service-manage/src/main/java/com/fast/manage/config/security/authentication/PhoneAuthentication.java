package com.fast.manage.config.security.authentication;

import com.fast.core.safe.entity.Authentication;
import lombok.Data;

/**
 * @program: fast
 * @description: 手机号认证
 * @author: 黄嘉浩
 * @create: 2023-06-03 16:59
 **/
@Data
public class PhoneAuthentication extends Authentication {
    /**
     * 手机号
     **/
    private String phone;
    /**
     * 验证码
     **/
    private String captcha;
}
