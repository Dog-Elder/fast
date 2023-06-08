package com.fast.manage;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: fast
 * @description:
 * @author: @黄嘉浩
 * @create: 2023-05-12 09:46
 **/
@Slf4j
@EnableCaching
@ServletComponentScan
@MapperScan(value = {"com.fast.*.dao"})
@ComponentScan(basePackages = {"com.fast"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
        log.info(
                "/\\_/\\\n" +
                "                                                                                                 ____/ o o \\\n" +
                "                                                                                               /~____  =-= /\n" +
                "                                                                                              (______)__m_m)");
    }
}
