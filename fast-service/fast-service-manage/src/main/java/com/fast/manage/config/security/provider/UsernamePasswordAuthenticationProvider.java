package com.fast.manage.config.security.provider;

import com.fast.core.safe.entity.Authentication;
import com.fast.common.service.AuthenticationProvider;
import com.fast.core.common.util.SUtil;
import com.fast.core.safe.util.ManageUtil;
import com.fast.manage.config.security.authentication.UserPasswordAuthentication;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

// 用户名密码认证提供者
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Override
    public boolean supports(Class<?> authenticationType) {
        return UserPasswordAuthentication.class.isAssignableFrom(authenticationType);
    }

    @Override
    public void authenticate(Authentication request) throws AuthenticationException {
        UserPasswordAuthentication voucher = (UserPasswordAuthentication) request;
        // 根据用户名和密码进行认证逻辑
        String username = voucher.getUsername();
        String password = voucher.getPassword();
        if (SUtil.isEmpty(username) || SUtil.isEmpty(password)) {
            throw new AuthenticationException("用户名或密码不能为空");
        }
        // 认证成功
        if ("123".equals(username)&&"123".equals(password)) {
            ManageUtil.login(1);

        } else {
            // 认证失败
            throw new AuthenticationException("用户名或密码无效");
        }
    }
}