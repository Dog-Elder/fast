package com.fast.core.boot.config;


import com.fast.core.common.util.UploadUtil;
import com.fast.core.common.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

import static com.fast.core.common.constant.Constants.RESOURCE_PREFIX;


@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //判断是否是win系统
        String profilePath = Util.isWin ? UploadUtil.PROFILE_WIN : UploadUtil.PROFILE_LINUX;
        log.info("加载静态资源路径:[{}]",profilePath);
        registry.addResourceHandler(RESOURCE_PREFIX+"/**")
                .addResourceLocations("file:"+profilePath+ File.separator);
    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
}