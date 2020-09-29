package com.tg.api.intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Autowired
    RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/img")
                .excludePathPatterns("login/ajaxLogin")
                .excludePathPatterns("/sendCode");
    }
}
