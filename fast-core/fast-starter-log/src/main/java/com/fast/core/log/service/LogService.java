package com.fast.core.log.service;

import com.fast.core.log.event.ApiLogEvent;
/**
 * 日志服务
 * @author 黄嘉浩
 **/
public interface LogService {
    void recordingSave(ApiLogEvent apiLogEvent);
}
