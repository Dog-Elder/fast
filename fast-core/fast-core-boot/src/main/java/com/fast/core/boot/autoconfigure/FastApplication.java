package com.fast.core.boot.autoconfigure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * 快速应用程序
 *
 * @author 黄嘉浩
 * @date 2023/07/26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableAsync
@EnableCaching
@ServletComponentScan
@MapperScan(value = {"com.fast.*.dao"})
@ComponentScan(basePackages = {"com.fast"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public @interface FastApplication {
}
