package com.fast.core.safe.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.strategy.SaStrategy;
import com.fast.core.safe.util.ManageUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * Sa-Token 整合 jwt (Simple 简单模式)
     **/
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

    @PostConstruct
    public void init() {
        rewriteSaStrategy();
        setUserStpLogic();
    }

    /**
     * 重写Sa-Token的注解处理器，增加注解合并功能
     **/
    public void rewriteSaStrategy() {
        SaStrategy.instance.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }

    /**
     * 为 StpUserUtil 注入 StpLogicJwt 实现
     */
    public void setUserStpLogic() {
        ManageUtil.setStpLogic(new StpLogicJwtForSimple(ManageUtil.TYPE));
    }

    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //  注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }
}
