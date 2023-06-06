package com.fast.manage.config.security.secure;

import com.fast.common.entity.base.User;
import com.fast.core.util.FastRedis;
import com.fast.manage.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 授权工具类
 *
 * @Author: 黄嘉浩
 * @Date: 2023-06-06 16:45
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class AutoUtil {
    private final FastRedis fastRedis;

    //TODO 作者:黄嘉浩 授权工具
//    private User
}
