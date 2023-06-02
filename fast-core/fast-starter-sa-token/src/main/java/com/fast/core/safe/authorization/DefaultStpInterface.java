package com.fast.core.safe.authorization;

import cn.dev33.satoken.stp.StpInterface;
import com.fast.core.common.exception.CustomException;
import com.fast.core.common.util.Util;
import com.fast.core.safe.service.AuthorizationService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 自定义权限验证接口扩展
 * 新增服务需要实现该接口
 **/
@Component
public class DefaultStpInterface implements StpInterface {

    private AuthorizationService service;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        if (!Objects.equals(loginType, service.getType())) {
            return null;
        }
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        if (!Objects.equals(loginType, service.getType())) {
            return null;
        }
        return null;
    }

    public DefaultStpInterface(AuthorizationService service) {
        Util.isNull(service,"需要实现授权接口AuthorizationService!");
        this.service = service;
    }
}
