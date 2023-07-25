package com.fast.manage;

import com.fast.core.boot.autoconfigure.FastApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @program: fast
 * @description:
 * @author: @黄嘉浩
 * @create: 2023-05-12 09:46
 **/
@EnableAsync
@Slf4j
@FastApplication
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
