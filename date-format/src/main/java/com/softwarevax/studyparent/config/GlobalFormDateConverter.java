package com.softwarevax.studyparent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class GlobalFormDateConverter implements Converter<String, Date>, DateFormatParse {

    @Override
    public Date convert(String source) {
        return parse(source, null, null);
    }
}
