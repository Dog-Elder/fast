package com.fast.service;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: fast
 * @description:
 * @author: @黄嘉浩
 * @create: 2023-05-12 09:46
 **/
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan
@ComponentScan(basePackages = {"com.fast"})
@MapperScan(value = {"com.fast.service.dao", "com.fast.service.*.dao"})
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
        log.info("神奇的事情发生了!");
    }
}
