package com.wyw.concurrency;

import com.wyw.concurrency.example.thread_local.RequestHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class HttpFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //通常需要进行强转
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("do filter,threadId:{},urlPattern:{}",
                Thread.currentThread().getId(),request.getServletPath());
        RequestHolder.add(Thread.currentThread().getId());
        //不拦截请求，只做苏剧处理，需调用filterChain.doFilter
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
