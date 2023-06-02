package com.fast.core.entity.base;

import lombok.Data;

/**
 * @Program: fast
 * @Description: 用户登录
 * @Author: 黄嘉浩
 * @Create: 2023-06-03 01:40
 **/
@Data
public class BaseLoginBody {
    /**
     * 登录方式
     **/
    private String loginType;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
}
