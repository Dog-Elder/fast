package com.fast.common.config.secure;

import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.util.SaFoxUtil;
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
    public static StpLogic stpLogic;

    /**
     * token令牌名称 (同时也是cookie名称)
     */
    public static String TOKEN_NAME;

    /**
     * 使用@PostConstruct注解，在初始化完成后进行一次性的依赖注入
     */
    @Autowired
    public void init(List<AccountManage> accountManages, FastRedis fastRedis, StpLogic stpLogic) {
        BaseAuthUtil.accountManages = accountManages;
        BaseAuthUtil.fastRedis = fastRedis;
        BaseAuthUtil.stpLogic = stpLogic;
    }

    @Value("${sa-token.token-name}")
    public void setTokenName(String tokenName) {
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
        String tokenValue = ContextHolder
                .get(RequestContext.class)
                .getRequestHeaderJson()
                .getStr(getLowerCaseTokenName());
        return getToken(tokenValue);
    }

    /**
     * 根据 Token 值获取真实的 Token 内容。
     * 非web环境获取不到真的的token
     *
     * @param tokenValue Token 值(Header中的参数，或者cookie中的参数)
     * @return 真实的 Token 内容，如果 Token 值不符合要求或为空，则返回 null
     */
    public static String getToken(String tokenValue) {
        // 配置文件中是否配置了 Token前缀
        String tokenPrefix = stpLogic.getConfigOrGlobal().getTokenPrefix();
        if (SaFoxUtil.isNotEmpty(tokenPrefix)) {
            if (SaFoxUtil.isEmpty(tokenValue)) {
                tokenValue = null;
            } else if (!tokenValue.startsWith(tokenPrefix + " ")) {
                tokenValue = null;
            } else {
                tokenValue = tokenValue.substring(tokenPrefix.length() + " ".length());
            }
        }
        return tokenValue;
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
