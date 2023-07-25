//package com.fast.core.common.config;
//
//import cn.hutool.json.JSONConfig;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.format.DateTimeFormatter;
//
///**
// * @Program: fast
// * @Description:
// * @Author: 黄嘉浩
// * @Create: 2023-07-26 02:39
// **/
//@Configuration
//
//public class HutoolJsonConfig {
//    @Bean
//    public JSONConfig jsonConfig() {
//        // 创建自定义的JSONConfig
//        JSONConfig config = new JSONConfig();
//
//        // 注册Java 8日期时间类型的序列化器1
//        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        return config;
//    }
//}
