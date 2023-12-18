package com.softwarevax.dateformat.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class DateFormatDeserializer extends JsonDeserializer<Date> implements DateFormatParse {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        String dateStr = "";
        try {
            dateStr = jsonParser.getText().trim();
            Class<?> clazz = jsonParser.getParsingContext().getCurrentValue().getClass();
            String currentName = jsonParser.getParsingContext().getCurrentName();
            return parse(dateStr, clazz, currentName);
        } catch (Exception e1) {
            log.error(e1.getMessage(), e1);
            throw new IllegalArgumentException("时间无法处理:" + dateStr);
        }
    }
}
