package com.softwarevax.dateformat.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 指定时区
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 用于处理返回的时间格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new DateFormatDeserializer());
        mapper.registerModule(module);
        converter.setObjectMapper(mapper);
        return converter;
    }

}
