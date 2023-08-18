package com.fast.manage.config.security.provider;

import cn.dev33.satoken.stp.SaLoginModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.entity.base.User;
import com.fast.common.service.AuthenticationProvider;
import com.fast.core.common.util.Md5Util;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.safe.constant.JwtConstant;
import com.fast.core.safe.entity.Authentication;
import com.fast.core.safe.util.ManageUtil;
import com.fast.core.util.CacheUtil;
import com.fast.manage.config.security.authentication.UserPasswordAuthentication;
import com.fast.manage.config.security.secure.AuthManageUtil;
import com.fast.manage.entity.SysUser;
import com.fast.manage.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

/**
 * 用户名密码身份验证提供者
 *
 * @author 黄嘉浩
 * @date 2023/08/17
 */
@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    /**
     * 用户服务
     */
    private final ISysUserService userService;

    /**
     * 支持
     *
     * @param authenticationType 验证类型
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> authenticationType) {
        return UserPasswordAuthentication.class.isAssignableFrom(authenticationType);
    }

    /**
     * 进行身份验证
     *
     * @param request 请求
     * @throws AuthenticationException 身份验证异常
     */
    @Override
    public void authenticate(Authentication request) throws AuthenticationException {
        UserPasswordAuthentication voucher = (UserPasswordAuthentication) request;
        //  根据用户名和密码进行认证逻辑
        String username = voucher.getUsername();
        String password = voucher.getPassword();
        if (SUtil.isEmpty(username) || SUtil.isEmpty(password)) {
            throw new AuthenticationException("用户名或密码不能为空");
        }
        // TODO 作者:黄嘉浩   待办 密码加密
        SysUser user = userService.getOne(new LambdaQueryWrapper<>(SysUser.class).eq(User::getUsername, username).or().eq(User::getCode, username));
        if (Util.isNotNull(user) && Md5Util.verifyPassword(password, user.getPassword())) {
            ManageUtil.login(user.getCode(), SaLoginModel.create()
                    .setExtra(JwtConstant.ADMINISTRATOR, user.isGod())
                    .setExtra(JwtConstant.LOGIC_TYPE, ManageUtil.TYPE)
                    .setExtra(JwtConstant.USER_ID, user.getId())
            );
            CacheUtil.put(AuthManageUtil.getUserInfoKeyPath(user.getCode()), user, CacheConstant.ONE_DAY);
        } else {
            //  认证失败
            throw new AuthenticationException("用户名或密码无效");
        }
    }
}