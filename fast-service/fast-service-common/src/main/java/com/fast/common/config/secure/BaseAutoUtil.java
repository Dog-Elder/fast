package com.fast.common.config.secure;

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
public class BaseAutoUtil {
    private static List<AccountManage> accountManages;
    private static FastRedis fastRedis;

    /**
     * token令牌名称 (同时也是cookie名称)
     */
    public static String TOKEN_NAME;

    /**
     * 使用@PostConstruct注解，在初始化完成后进行一次性的依赖注入
     */
    @Autowired
    public void init(List<AccountManage> accountManages, FastRedis fastRedis) {
        BaseAutoUtil.accountManages = accountManages;
        BaseAutoUtil.fastRedis = fastRedis;
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
     * 可在非web环境使用
     * 获取登录id根据tokenValue
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
}
