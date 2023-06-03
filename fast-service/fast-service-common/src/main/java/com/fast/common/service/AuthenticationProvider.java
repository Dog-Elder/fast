package com.fast.common.service;

import com.fast.core.safe.entity.Authentication;

import javax.naming.AuthenticationException;

/**
 * @Program: fast
 * @Description: 认证相关接口
 * @Author: 黄嘉浩
 * @Create: 2023-06-03 01:45
 **/
public interface AuthenticationProvider {
    boolean supports(Class<?> authenticationType);

    void authenticate(Authentication body) throws AuthenticationException;
}
