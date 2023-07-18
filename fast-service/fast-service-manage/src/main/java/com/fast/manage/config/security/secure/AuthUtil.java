package com.fast.manage.config.security.secure;

import com.fast.common.config.secure.BaseAuthUtil;
import com.fast.common.entity.base.User;
import com.fast.core.safe.util.ManageUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 授权工具类
 * 针对多账户问题不要直接调用 ManageUtil
 * 因为 多账号 可以进行拓展
 * @Author: 黄嘉浩
 * @Date: 2023-06-06 16:45
 **/
@Slf4j
public class AuthUtil extends BaseAuthUtil {

    /**
     *
     **/
    public static User getUser() {
        getLoginAccountType(getToken())
    }


}
