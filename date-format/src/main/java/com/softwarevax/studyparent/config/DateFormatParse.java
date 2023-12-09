package com.softwarevax.studyparent.config;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface DateFormatParse {

    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    default Date parse(String dateText, Class<?> clazz, String fieldName) {
        try {
            // 1、处理时间戳类型（毫秒级）
            if(dateText.matches("\\d*")) {
                long time = Long.parseLong(dateText);
                return new Date(time);
            }
            // 2、处理有注解的情况，注解优先级最高，可单独处理一些字段，仅针对Content-Type=application/json
            if(!Objects.isNull(clazz) && StringUtils.hasText(fieldName)) {
                Field field = ReflectionUtils.findField(clazz, fieldName);
                DateTimeFormat dateFormat = AnnotationUtils.getAnnotation(field, DateTimeFormat.class);
                if(!Objects.isNull(dateFormat)) {
                    String pattern = dateFormat.pattern();
                    if(StringUtils.hasText(pattern)) {
                        // 有注解时
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        return sdf.parse(dateText);
                    }
                }
            }
            // 3、自动适配
            String[] date = new String[6];
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(dateText);
            int matchNum = 0;
            for (int i = 0, size = date.length; i < size; i++) {
                if(matcher.find()) {
                    matchNum++;
                    String group = matcher.group();
                    if(i == 0) {
                        date[i] = group.length() < 4 ? String.valueOf(Integer.parseInt(group) + 2000) : group;
                    }
                    if(i >= 1) {
                        date[i] = group.length() < 2 ? ("0" + group) : group;
                    }
                }
                if(Objects.isNull(date[i]) || "".equals(date[i])) {
                    date[i] = "00";
                }
            }
            if(matchNum < 3) {
                throw new IllegalArgumentException("时间无法处理:" + dateText);
            }
            String time = String.format("%s-%s-%s %s:%s:%s", date[0], date[1], date[2], date[3], date[4], date[5]);
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            return sdf.parse(time);
        } catch (ParseException e) {
            throw new IllegalArgumentException("时间无法处理:" + dateText);
        }
    }

}
