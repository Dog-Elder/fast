package com.fast.manage.config.security.secure;

import com.fast.common.config.secure.BaseAuthUtil;
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
        // TODO 作者:黄嘉浩
//        CacheUtil.getCache()
        return null;
    }

    /**
     * 获取用户code
     *
     * @return {@link SysUser#code}
     */
    public static String getUserCode() {
        Object loginId = ManageUtil.getLoginId();
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
