package com.fast.manage.config.security.realms;

import cn.hutool.jwt.JWT;
import com.fast.core.safe.entity.Authentication;
import com.fast.common.service.AuthenticationProvider;
import com.fast.core.safe.service.SecurityManagerService;
import com.fast.core.safe.util.ManageUtil;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: fast
 * @description: 后台认证/授权 管理领域
 * @author: 黄嘉浩
 * @create: 2023-06-03 15:23
 **/
@Component
public class ManageRealms implements SecurityManagerService {
    private List<AuthenticationProvider> authenticationProviders;

    public ManageRealms(List<AuthenticationProvider> authenticationProviders) {
        this.authenticationProviders = authenticationProviders;
    }

    @Override
    public String getType() {
        return ManageUtil.TYPE;
    }

    @Override
    public void authenticate(Authentication request) throws AuthenticationException {
        for (AuthenticationProvider provider : authenticationProviders) {
            if (provider.supports(request.getClass())) {
                provider.authenticate(request);
                return;
            }
        }
        throw new AuthenticationException("不支持的身份验证类型");
    }

    @Override
    public List<String> getPermissionList(Object loginId) {
        List<String> list = new ArrayList<>();
        list.add("*");
        return list;
    }

    @Override
    public List<String> getRoleList(Object loginId) {
        return null;
    }

}
