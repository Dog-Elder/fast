package com.fast.manage.controller;

import cn.hutool.json.JSONUtil;
import com.fast.core.common.domain.domain.R;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.safe.service.SecurityManagerService;
import com.fast.core.safe.util.ManageUtil;
import com.fast.manage.config.security.authentication.UserPasswordAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

/**
 * 认证授权
 *
 * @Program: fast
 * @Description: 服务登录
 * @Author: 黄嘉浩
 * @Create: 2023-06-03 01:31
 **/
@RestController
@RequestMapping("${fast.api-prefix.manage}")
@RequiredArgsConstructor
public class SysTokenController {
    private final SecurityManagerService service;

    /**
     * 登录接口
     *
     * @param req:
     * @Author: 黄嘉浩
     * @Date: 2023/6/5 20:23
     * @Return: com.fast.core.common.domain.domain.R
     **/
    @PostMapping("/login")
    public R login(@RequestBody UserPasswordAuthentication req) {
        try {
            service.authenticate(req);
        } catch (AuthenticationException e) {
            throw new ServiceException(e.getMessage());
        }
        return R.success(JSONUtil.parseObj(ManageUtil.getTokenInfo()));
    }
    /**
     * 用户注销
     *
     * @Author: 黄嘉浩
     * @Date: 2023/6/5 20:23
     * @Return: com.fast.core.common.domain.domain.R
     **/
    @PostMapping("/logout")
    public R logout() {
        ManageUtil.logout();
        return R.success();
    }
}
