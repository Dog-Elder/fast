package com.fast.core.log.listening;

import com.fast.core.log.event.ApiLogEvent;
import com.fast.core.log.service.LogService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * API 访问事件监听
 *
 * @author 黄嘉浩
 * @date 2023-07-06 17:05
 **/
@Component
public class ApiLogListening {

    private LogService service;

    public ApiLogListening(LogService service) {
        this.service = service;
    }

    @Async
    @EventListener
    public void saveApiLog(ApiLogEvent apiLogEvent){
        service.recordingSave(apiLogEvent);
    }

}
