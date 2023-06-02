package com.fast.manage.service.impl;

import com.fast.core.safe.service.AuthorizationService;
import com.fast.core.safe.util.ManageUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program: fast
 * @Description: 后台用户授权服务接口实例
 * @Author: 黄嘉浩
 * @Create: 2023-06-03 01:20
 **/
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public String getType() {
        return ManageUtil.TYPE;
    }

    @Override
    public List<String> getPermissionList(Object loginId) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId) {
        return null;
    }
}
