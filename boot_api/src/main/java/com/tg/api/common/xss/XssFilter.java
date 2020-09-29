package com.tg.api.common.xss;

import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.servlet.*;
/**
 * XSS过滤
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {
	}

}