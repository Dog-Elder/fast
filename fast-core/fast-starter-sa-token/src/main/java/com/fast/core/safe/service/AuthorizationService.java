package com.fast.core.safe.service;

import com.fast.core.safe.util.ManageUtil;

import java.util.List;

/**
 * 授权服务
 **/
public interface AuthorizationService {
    /**
     * 认证TYPE
     * 例如 {@link ManageUtil#TYPE}
     **/
    String getType();

    /**
     * @param loginId: 用户登录id
     * @Description: 获取权限列表
     * @Author: 黄嘉浩
     * @Date: 2023/6/2 20:38
     * @return: java.util.List<java.lang.String> 权限集合
     **/
    List<String> getPermissionList(Object loginId);

    /**
     * @param loginId: 用户登录id
     * @Description: 获取角色列表
     * @Author: 黄嘉浩
     * @Date: 2023/6/2 20:38
     * @return: java.util.List<java.lang.String> 角色集合
     **/
    List<String> getRoleList(Object loginId);
}
