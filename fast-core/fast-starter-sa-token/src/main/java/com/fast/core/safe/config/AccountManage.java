package com.fast.core.safe.config;

import com.fast.core.safe.entity.Authentication;

/**
 * sa-token账号类型管理
 *
 * @author 黄嘉浩
 * @date 2023-07-18 14:57
 **/
public interface AccountManage {

    /**
     * 逻辑类型
     */
    String LOGIC_TYPE = "LogicType";

    /**
     * 从jwt中获取登录类型,来进行判断
     * 在登录的时候需要把类型进行填充到jwt中,否则无法比较
     * 例如
     *
     * @see com.fast.manage.config.security.provider.UsernamePasswordAuthenticationProvider#authenticate(Authentication) 登录添加类型
     **/
    default boolean supports(String token) {
        return false;
    }

    /**
     * 获得登录id
     *
     * @param tokenValue 令牌值
     * @return {@link Object}
     */
    default Object getLoginId(String tokenValue) {
        return null;
    }

    /**
     * 获得登录帐户类型
     *
     * @return {@link String} 类型
     */
    default String getLoginAccountType() {
        return null;
    }

}
