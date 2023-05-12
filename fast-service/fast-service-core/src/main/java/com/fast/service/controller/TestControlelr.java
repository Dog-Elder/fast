package com.fast.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: fast
 * @description:
 * @author: @黄嘉浩
 * @create: 2023-05-12 17:10
 **/
@RestController
@RequestMapping("/test")
public class TestControlelr {
    @Value("${test.name}")
    String name;
    @GetMapping
    public void test(){
        System.out.println("name = " + name);
    }
}
