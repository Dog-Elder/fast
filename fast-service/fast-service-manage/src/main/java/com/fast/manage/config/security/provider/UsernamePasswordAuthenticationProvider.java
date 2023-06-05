package com.fast.manage.config.security.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fast.common.service.AuthenticationProvider;
import com.fast.core.common.util.Md5Util;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.safe.entity.Authentication;
import com.fast.core.safe.util.ManageUtil;
import com.fast.manage.config.security.authentication.UserPasswordAuthentication;
import com.fast.manage.entity.SysUser;
import com.fast.manage.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

// 用户名密码认证提供者
@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final ISysUserService userService;

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
        //TODO 作者:黄嘉浩   待办 密码加密
        SysUser user = userService.getOne(new QueryWrapper<>(new SysUser().setUsername(username)));
        if (Util.isNotNull(user) && Md5Util.verifyPassword(password, user.getPassword())) {
            //TODO 作者:黄嘉浩 后期使用编码 先使用id
            ManageUtil.login(user.getId());
        } else {
            // 认证失败
            throw new AuthenticationException("用户名或密码无效");
        }
    }
}