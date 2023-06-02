package com.fast.manage.controller;

import cn.hutool.json.JSONUtil;
import com.fast.core.common.domain.domain.R;
import com.fast.core.entity.base.BaseLoginBody;
import com.fast.core.safe.util.ManageUtil;
import com.fast.core.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Program: fast
 * @Description: 服务登录
 * @Author: 黄嘉浩
 * @Create: 2023-06-03 01:31
 **/
@RestController
@RequestMapping("/manage-api")
@RequiredArgsConstructor
public class SysTokenController {
    private final TokenService tokenService;
    @PostMapping("/login")
    public R login(@RequestBody BaseLoginBody body){

        return R.success(JSONUtil.parseObj(ManageUtil.getTokenInfo()));
    }
}
