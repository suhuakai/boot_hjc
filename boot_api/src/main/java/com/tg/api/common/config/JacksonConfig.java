package com.tg.api.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.tg.api.common.bitcoin.JSONUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.converter.HttpMessageConverter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

@Configuration
@Log4j2
public class JacksonConfig {
    private static final Logger logger = LoggerFactory.getLogger(JacksonConfig.class);
    private static final Properties properties = new Properties();

    /**
     * 注入Bean : HttpMessageConverters
     *
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    public synchronized static void init(String resourcePattern) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(JacksonConfig.class.getClassLoader());
        Resource[] resources = resolver.getResources(resourcePattern);
        for (Resource resource : resources) {
            if (log.isDebugEnabled()) {
                log.debug("加载Properties配置文件{}", resource.getFilename());
            }
            properties.load(new InputStreamReader(resource.getInputStream(), "UTF-8"));
        }
        if (logger.isDebugEnabled()) {
            Map<String, Object> configuration = MapUtils.typedSortedMap(new TreeMap(properties), String.class, Object.class);
            logger.debug("****************系统配置参数表****************\n{}", JSONUtils.toPrettyJsonLoosely(configuration));
        }
    }

    /**
     * 根据key找出value值
     * @param key 键
     * @return String
     * @author
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
