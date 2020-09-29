package com.tg.api.intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * filter过滤器 将流继续写入到请求request
 */

//@Configuration
public class MyFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
