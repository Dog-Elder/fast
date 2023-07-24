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
import lombok.extern.slf4j.Slf4j;

/**
 * 管理端授权工具类
 * 相当于是的拓展 {@link ManageUtil}
 *
 * @author 黄嘉浩
 * @date 2023-06-06 16:45
 **/
@Slf4j
public class AuthManageUtil extends BaseAuthUtil {

    /**
     * 获取当前登录用户
     **/
    public static SysUser getUser() {
        User user = ContextHolder.get(User.class);
        if (Util.isNotNull(user)) {
            return (SysUser) user;
        }
        user = fastRedis.getObject(getUserInfoKeyPath(getUserCode()), User.class);
        if (Util.isNull(user)) {
            return null;
        }
        ContextHolder.put(User.class, user);
        return (SysUser) user;
    }

    /**
     * 获取当前登录用户表id
     *
     * @return {@link User#id}
     **/
    public static String getUserId() {
        return (String)ManageUtil.getExtra(JwtConstant.USER_ID);
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

}
