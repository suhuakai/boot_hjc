package com.tg.api.common.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @Author: Wy
 * @Email: DysonWy@foxMail.com
 * @Time: 2020/3/10 17:30
 * @Description:
 */
@Configuration
public class UploadConfig {
    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        DataSize dataSize = DataSize.ofMegabytes(10);
        factory.setMaxFileSize(dataSize); //MB
        //factory.setMaxFileSize("5MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(dataSize);
        //factory.setMaxRequestSize("5MB");
        return factory.createMultipartConfig();
    }
}
