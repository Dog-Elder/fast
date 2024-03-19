package com.fast.common.entity.sys;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.core.common.validate.annotation.Display;
import com.fast.core.mybatis.annotation.AutoFill;
import com.fast.core.mybatis.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import static com.fast.core.mybatis.constant.PublicFieldConstant.UPDATE_BY;
import static com.fast.core.mybatis.constant.PublicFieldConstant.UPDATE_TIME;

/**
 * 接口请求日志
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-07-10
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName(value = "sys_request_logs", excludeProperty = {UPDATE_BY, UPDATE_TIME})
@EqualsAndHashCode(callSuper = false)
@AutoFill(createBy = false, updateBy = false, updateTime = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysRequestLogs extends BaseEntity {

    /**
     * 请求标识
     */
    @Display("请求标识")
    @TableField("request_id")
    private String requestId;

    /**
     * 来源ip
     */
    @Display("来源ip")
    @TableField("ip")
    private String ip;

    /**
     * 请求方式
     */
    @Display("请求方式")
    @TableField("request_manner")
    private String requestManner;

    /**
     * 请求头
     */
    @Display("请求头")
    @TableField("request_headers")
    private String requestHeaders;

    /**
     * 请求头JSON
     */
    @TableField(exist = false)
    private JSONObject requestHeaderJson;

    /**
     * 请求路径
     */
    @Display("请求路径")
    @TableField("request_path")
    private String requestPath;

    /**
     * 请求方法
     */
    @Display("请求方法")
    @TableField("request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @Display("请求参数")
    @TableField("request_parameters")
    private String requestParameters;

    /**
     * 请求正文
     */
    @Display("请求正文")
    @TableField("request_body")
    private String requestBody;

    /**
     * 响应体
     */
    @Display("响应体")
    @TableField("responder_body")
    private String responderBody;

    /**
     * 请求进入时间(当前时间毫秒)
     */
    @Display("请求进入时间(当前时间毫秒)")
    @TableField("request_entry_time")
    private Long requestEntryTime;

    /**
     * 请求结束时间(毫秒)
     */
    @Display("请求结束时间(毫秒)")
    @TableField("request_end_times")
    private Long requestEndTimes;

    @Display("耗时")
    @TableField("take")
    private Long take;

    @Display("操作账户类型")
    @TableField("create_by_type")
    private String createByType;


    @Display("状态码")
    @TableField("response_code")
    private String responseCode;
}