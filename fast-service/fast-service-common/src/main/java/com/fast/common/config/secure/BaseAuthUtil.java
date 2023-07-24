package com.fast.common.config.secure;

import com.fast.core.common.context.ContextHolder;
import com.fast.core.log.model.RequestContext;
import com.fast.core.safe.config.AccountManage;
import com.fast.core.util.FastRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基础授权工具
 *
 * @author 黄嘉浩
 * @date 2023-07-18 11:25
 **/
@Slf4j
@Component
public class BaseAuthUtil {
    public static List<AccountManage> accountManages;
    public static FastRedis fastRedis;

    /**
     * token令牌名称 (同时也是cookie名称)
     */
    public static String TOKEN_NAME;

    /**
     * 使用@PostConstruct注解，在初始化完成后进行一次性的依赖注入
     */
    @Autowired
    public void init(List<AccountManage> accountManages, FastRedis fastRedis) {
        BaseAuthUtil.accountManages = accountManages;
        BaseAuthUtil.fastRedis = fastRedis;
    }

    @Value("${sa-token.token-name}")
    public void setTOKEN_NAME(String tokenName) {
        TOKEN_NAME = tokenName;
    }

    /**
     * 获取小写令牌名称
     *
     * @return {@link String}
     */
    public static String getLowerCaseTokenName() {
        return TOKEN_NAME.toLowerCase();
    }


    /**
     * 获得令牌
     * 只有web环境才能获取到
     * 非web环境获取不到
     * Header中没有命名token
     *
     * @return {@link String}
     */
    public static String getToken() {
        // 从上下文线程中获取参数
        return ContextHolder
                .get(RequestContext.class)
                .getRequestHeaderJson()
                .getStr(getLowerCaseTokenName());
    }

    /**
     * 获取登录id根据tokenValue
     * 可在非web环境使用
     *
     * @param tokenValue 令牌值
     * @return {@link Object}
     */
    public static Object getLoginIdByToken(String tokenValue) {
        for (AccountManage manage : accountManages) {
            if (manage.supports(tokenValue)) {
                return manage.getLoginId(tokenValue);
            }
        }
        return null;
    }

    /**
     * 获取登录id
     * 仅在web环境使用
     *
     * @return {@link Object}
     */
    public static Object getLoginId() {
        return getLoginIdByToken(getToken());
    }

    /**
     * 可在非web环境使用
     * 获取登录账户类型 根据tokenValue
     *
     * @param tokenValue 令牌值
     * @return {@link String}
     */
    public static String getLoginAccountType(String tokenValue) {
        for (AccountManage manage : accountManages) {
            if (manage.supports(tokenValue)) {
                return manage.getLoginAccountType();
            }
        }
        return null;
    }

    /**
     * 获取登录账户类型
     * 仅在web环境使用
     *
     * @return {@link String} 账号类型
     */
    public static String getLoginAccountType() {
        return getLoginAccountType(getToken());
    }

}
