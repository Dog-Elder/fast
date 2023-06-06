package com.fast.manage.controller;

import cn.hutool.json.JSONUtil;
import com.fast.core.common.exception.CustomException;
import com.fast.core.safe.entity.Authentication;
import com.fast.core.common.domain.domain.R;
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
@RequestMapping("/manage-api")
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
            throw new CustomException(e.getMessage());
        }
        return R.success(JSONUtil.parseObj(ManageUtil.getTokenInfo()));
    }
}
