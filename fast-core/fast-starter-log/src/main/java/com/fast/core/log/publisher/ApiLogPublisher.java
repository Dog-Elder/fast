package com.fast.core.log.publisher;

import cn.hutool.extra.spring.SpringUtil;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.log.model.RequestContext;
import com.fast.core.log.event.ApiLogEvent;

/**
 * API log 事件发送
 *
 * @author 黄嘉浩
 * @date 2023-07-05 17:28
 **/
public class ApiLogPublisher {
    public static void publishEvent(RequestContext context) {
        ApiLogEvent apiLogEvent = BUtil.copy(context, ApiLogEvent.class);
        SpringUtil.publishEvent(apiLogEvent);
    }
}
