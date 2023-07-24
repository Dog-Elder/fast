package com.fast.core.boot.filter;

import cn.hutool.core.util.IdUtil;
import com.fast.core.common.context.ContextHolder;
import com.fast.core.common.util.WebUtil;
import com.fast.core.log.model.RequestContext;
import com.fast.core.log.publisher.ApiLogPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
@WebFilter(filterName = "ApiAccessFilter", urlPatterns = "/*")
public class ApiAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        long start = System.currentTimeMillis(); //  请求进入时间

        //  设置其他请求相关信息...
        String requestId = IdUtil.fastSimpleUUID();
        RequestContext requestContext = new RequestContext();
        requestContext.setRequestId(requestId);
        requestContext.setRequestEntryTime(start);
        requestContext.setIp(WebUtil.getIP(request));

        //  将请求上下文绑定到当前线程
        ContextHolder.put(RequestContext.class, requestContext);

        log.info("[Api Access] start. id: {}, uri: {}, method: {}, client: {}", requestId,
                request.getRequestURI(), request.getMethod(), WebUtil.getIP(request));

        try {
            filterChain.doFilter(servletRequest, servletResponse);
            RequestContext context = ContextHolder.get(RequestContext.class);
            context.setRequestEndTimes();
            ApiLogPublisher.publishEvent(context);
        } finally {
            //  清理请求上下文
            ContextHolder.remove(RequestContext.class);
        }

        log.info("[Api Access] end. id: {}, duration: {}ms", requestId,
                System.currentTimeMillis() - start);

    }


}