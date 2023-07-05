package com.fast.core.boot.filter;

import cn.hutool.core.util.IdUtil;
import com.fast.core.boot.model.RequestContext;
import com.fast.core.boot.util.RequestContextHolder;
import com.fast.core.common.util.WebUtil;
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

        String requestId = IdUtil.fastSimpleUUID();
        RequestContext requestContext = new RequestContext();
        // 设置其他请求相关信息...

        // 将请求上下文绑定到当前线程
        RequestContextHolder.setContext(requestContext);

        long start = System.currentTimeMillis(); // 请求进入时间

        log.info("[Api Access] start. id: {}, uri: {}, method: {}, client: {}", requestId,
                request.getRequestURI(), request.getMethod(), WebUtil.getIP(request));

        try {
            filterChain.doFilter(servletRequest, servletResponse);
            RequestContext context = RequestContextHolder.getContext();
            context.setRequestEndTimes();
            System.out.println("响应结束 = " + context);
        } finally {
            // 清理请求上下文
            RequestContextHolder.clearContext();
        }

        log.info("[Api Access] end. id: {}, duration: {}ms", requestId,
                System.currentTimeMillis() - start);

    }


}