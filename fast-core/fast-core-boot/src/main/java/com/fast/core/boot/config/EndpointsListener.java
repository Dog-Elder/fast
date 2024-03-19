package com.fast.core.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 端点侦听器
 *
 * @author 黄嘉浩
 * @date 2024/03/11
 */
@Slf4j
@Component
public class EndpointsListener implements ApplicationListener<ServletWebServerInitializedEvent> {

    /**
     * 处理程序映射
     */
    @Resource
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;


    /**
     * 在应用程序事件中
     *
     * @param event 事件
     */
    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        handlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            Method method = handlerMethod.getMethod();
            log.debug("URL模式: " + requestMappingInfo.getPatternsCondition());
            log.debug("HTTP方法: " + requestMappingInfo.getMethodsCondition());
            log.debug("控制器类: " + method.getDeclaringClass().getName());
            log.debug("处理方法: " + method.getName());

            // 从方法中获取自定义注解
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                // 检查注解是否是您感兴趣的自定义注解
//                if (annotation instanceof YourCustomAnnotation) {
//                    YourCustomAnnotation yourAnnotation = (YourCustomAnnotation) annotation;
//                    // 使用您的自定义注解中的属性
//                    System.out.println("自定义注解属性: " + yourAnnotation.attributeName());
//                }
            }
            log.debug("----");
        });
    }
}