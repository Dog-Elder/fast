package com.fast.core.boot.config;


//import com.fast.core.boot.interceptor.RequestInterceptor;

import com.fast.core.boot.interceptor.RequestInterceptor;
import com.fast.core.boot.interceptor.servlet.DispatcherServlet;
import com.fast.core.common.util.UploadUtil;
import com.fast.core.common.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

import static com.fast.core.common.constant.Constants.RESOURCE_PREFIX;


@Slf4j
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private RequestInterceptor requestInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //判断是否是win系统
        String profilePath = Util.isWin ? UploadUtil.PROFILE_WIN : UploadUtil.PROFILE_LINUX;
        log.info("加载静态资源路径:[{}]", profilePath);
        registry.addResourceHandler(RESOURCE_PREFIX + "/**")
                .addResourceLocations("file:" + profilePath + File.separator);
    }

    @Bean
    @Qualifier(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public org.springframework.web.servlet.DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");;
    }

}