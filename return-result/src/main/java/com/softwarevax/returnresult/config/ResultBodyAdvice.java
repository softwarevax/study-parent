package com.softwarevax.returnresult.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;

@RestControllerAdvice
public class ResultBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return !methodParameter.hasMethodAnnotation(IgnoreWrap.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter method, MediaType mediaType, Class<? extends HttpMessageConverter<?>> clazz, ServerHttpRequest request, ServerHttpResponse response) {
        if (o instanceof String) {
            // 返回json字符串
            return ResultDto.success(o);
        } else if(o.getClass() == LinkedHashMap.class) {
            LinkedHashMap map = (LinkedHashMap) o;
            if(map.containsKey("error")) {
                // 处理错误或异常
                int code = (Integer) map.get("status");
                String message = (String) map.get("message");
                ResultDto dto = new ResultDto(false, null, code, message);
                return dto;
            }
        } else if (ResultDto.class.isAssignableFrom(o.getClass())) {
            // 如果返回的类型是DTO，则不处理
            return o;
        }
        // 默认包装一层DTO
        return ResultDto.successT(o);
    }
}
