package com.fast.core.safe.authorization;

import cn.dev33.satoken.stp.StpInterface;
import com.fast.core.safe.service.AuthorizationService;
import org.springframework.stereotype.Component;

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
        return service.getPermissionList(loginId);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        if (!Objects.equals(loginType, service.getType())) {
            return null;
        }
        return service.getRoleList(loginId);
    }

    public DefaultStpInterface(AuthorizationService service) {
        this.service = service;
    }
}
