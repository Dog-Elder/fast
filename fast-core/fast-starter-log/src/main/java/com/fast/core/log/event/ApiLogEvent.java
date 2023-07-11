package com.fast.core.log.event;


import lombok.Data;

/**
 * Api访问事件
 *
 * @author 黄嘉浩
 * @date 2023-07-05 17:08
 **/
@Data
public class ApiLogEvent {
    /**
     * 请求标识
     **/
    private String requestId;
    /**
     * 来源ip
     **/
    private String ip;
    /**
     * 请求方式
     */
    private String requestManner;
    /**
     * 请求头
     **/
    private String requestHeaders;
    /**
     * 请求路径
     **/
    private String requestPath;
    /**
     * 请求方法
     **/
    private String requestMethod;
    /**
     * 请求参数
     **/
    private String requestParameters;
    /**
     * 请求正文
     **/
    private String requestBody;
    /**
     * 响应体
     **/
    private String responderBody;
    /**
     * 请求进入时间(当前时间毫秒)
     **/
    private Long requestEntryTime;
    /**
     * 请求结束时间(毫秒)
     **/
    private Long requestEndTimes;

    public void setRequestEndTimes() {
        this.requestEndTimes = System.currentTimeMillis();
    }

    /**
     * 获取执行时间(毫秒)
     **/
    public long getDuration() {
        return requestEndTimes - requestEntryTime;
    }
}
