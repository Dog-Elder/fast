package com.fast.core.boot.config;



import com.fast.core.boot.filter.ApiAccessFilter;
import com.fast.core.boot.interceptor.RequestInterceptor;
import com.fast.core.boot.interceptor.servlet.DispatcherServlet;
import com.fast.core.common.util.FileUploader;
import com.fast.core.common.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.io.File;

import static com.fast.core.common.constant.Constants.RESOURCE_PREFIX;


@Slf4j
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private RequestInterceptor requestInterceptor;

    /**
     * 添加资源处理程序
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 判断是否是win系统
        String profilePath = Util.isWin ? FileUploader.PROFILE_WIN : FileUploader.PROFILE_LINUX;
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
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/**");
        // TODO url访问404会自动跳转到/error访问Spring自带的路径 现无法解决 特殊处理 requestInterceptor中有404方法
//                .excludePathPatterns("/error"); // 排除 /error 路径
    }

    @Bean
    public FilterRegistrationBean<ApiAccessFilter> apiAccessFilter() {
        FilterRegistrationBean<ApiAccessFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiAccessFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}