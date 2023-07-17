package com.fast.manage.config.security.provider;

import com.fast.core.safe.entity.Authentication;
import com.fast.common.service.AuthenticationProvider;
import com.fast.manage.config.security.authentication.PhoneAuthentication;
import org.springframework.stereotype.Component;

//  手机号验证码认证提供者
@Component
public class PhoneAuthenticationProvider implements AuthenticationProvider {
    @Override
    public boolean supports(Class<?> authenticationType) {
        return PhoneAuthentication.class.isAssignableFrom(authenticationType);
    }

    @Override
    public void authenticate(Authentication authentication) {
        PhoneAuthentication voucher = (PhoneAuthentication) authentication;

        //  根据手机号和验证码进行认证逻辑
        //  验证手机号和验证码是否匹配等
        System.out.println("根据手机号和验证码进行认证逻辑 " + voucher);
    }
}