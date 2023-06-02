package com.fast.core.service;

import com.fast.core.entity.base.BaseLoginBody;

/**
 * @Program: fast
 * @Description: 认证相关接口
 * @Author: 黄嘉浩
 * @Create: 2023-06-03 01:45
 **/
public interface TokenService {
    void authentication(BaseLoginBody body);
}
