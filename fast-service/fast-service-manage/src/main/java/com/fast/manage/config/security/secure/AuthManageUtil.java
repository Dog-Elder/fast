package com.fast.manage.config.security.secure;

import com.fast.common.config.secure.BaseAuthUtil;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.entity.base.User;
import com.fast.core.common.context.ContextHolder;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.safe.constant.JwtConstant;
import com.fast.core.safe.util.ManageUtil;
import com.fast.manage.entity.SysUser;
import com.fast.manage.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 管理端授权工具类
 * 相当于是的拓展 {@link ManageUtil}
 *
 * @author 黄嘉浩
 * @date 2023-06-06 16:45
 **/
@Slf4j
@Component
public class AuthManageUtil extends BaseAuthUtil {

    private static ISysUserService sysUserService;

    @Autowired
    public void init(ISysUserService sysUserService) {
        AuthManageUtil.sysUserService = sysUserService;
    }

    /**
     * 获取当前登录用户
     **/
    public static SysUser getUser() {
        // 尝试从上下文中获取
        SysUser user = ContextHolder.get(SysUser.class);
        if (Util.isNotNull(user)) {
            return user;
        }
        user = sysUserService.getUserByCode(getUserCode());
        if (Util.isNull(user)) {
            return null;
        }
        // 存放至上下文方便二次使用
        ContextHolder.put(SysUser.class, user);
        return user;
    }

    /**
     * 获取当前登录用户表id
     *
     * @return {@link User#id}
     **/
    public static String getUserId() {
        return (String) ManageUtil.getExtra(JwtConstant.USER_ID);
    }

    /**
     * 获取用户code
     *
     * @return {@link SysUser#code}
     */
    public static String getUserCode() {
        Object loginId = getLoginId();
        if (Util.isNotNull(loginId)) {
            return loginId.toString();
        }
        return null;
    }

    /**
     * 当前是否是超管
     *
     * @return boolean
     */
    public static boolean isAdministrator() {
        return (Boolean) ManageUtil.getExtra(JwtConstant.ADMINISTRATOR);
    }

    /**
     * 得到用户信息RedisKeyPath
     * 仅在web环境使用
     *
     * @param userCode 用户编码
     * @return {@link String}
     */
    public static String getUserInfoKeyPath(String userCode) {
        if (SUtil.isBlank(userCode)) {
            return null;
        }
        return SUtil.format(CacheConstant.MANAGE_USER + ManageUtil.TYPE + ":" + CacheConstant.User.INFO, userCode);
    }

}
