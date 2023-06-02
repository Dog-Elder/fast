package com.fast.manage.service.impl;

import com.fast.core.entity.base.BaseLoginBody;
import com.fast.core.service.TokenService;
import com.fast.manage.service.ISysAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Program: fast
 * @Description: 后台认证
 * @Author: 黄嘉浩
 * @Create: 2023-06-03 01:51
 **/
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final ISysAdminService adminService;
    @Override
    public void authentication(BaseLoginBody body) {
    }
}
