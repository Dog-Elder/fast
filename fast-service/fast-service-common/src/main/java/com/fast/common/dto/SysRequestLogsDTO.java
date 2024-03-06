package com.fast.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 接口请求日志
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @see com.fast.common.entity.sys.SysRequestLogs
 * @since 1.0.0 2024-03-02
 */
@Data
public class SysRequestLogsDTO {

    /**
     * id
     */
    @Display("id")
    private String id;

    /**
     * 请求标识
     */
    @Display("请求标识")
    private String requestId;

    /**
     * 来源ip
     */
    @Display("来源ip")
    private String ip;

    /**
     * 请求方式
     */
    @Display("请求方式")
    private String requestManner;

    /**
     * 请求头
     */
    @Display("请求头")
    private String requestHeaders;

    /**
     * 请求路径
     */
    @Display("请求路径")
    private String requestPath;

    /**
     * 请求方法
     */
    @Display("请求方法")
    private String requestMethod;

    /**
     * 请求参数
     */
    @Display("请求参数")
    private String requestParameters;

    /**
     * 请求正文
     */
    @Display("请求正文")
    private String requestBody;

    /**
     * 响应体
     */
    @Display("响应体")
    private String responderBody;

    /**
     * 请求进入时间(当前时间毫秒)
     */
    @Display("请求进入时间(当前时间毫秒)")
    private String requestEntryTime;

    /**
     * 请求结束时间(毫秒)
     */
    @Display("请求结束时间(毫秒)")
    private String requestEndTimes;

    /**
     * 耗时
     */
    @Display("耗时")
    private String take;


    /**
     * 创建时间
     */
    @Display("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 备注信息
     */
    @Display("备注信息")
    private String remark;

    /**
     * 创建者
     */
    @Display("创建者")
    private String createBy;

    /**
     * 创建者账户类型
     */
    @Display("创建者账户类型")
    private String createByType;

    /**
     * 状态码
     */
    @Display("状态码")
    private String responseCode;


}