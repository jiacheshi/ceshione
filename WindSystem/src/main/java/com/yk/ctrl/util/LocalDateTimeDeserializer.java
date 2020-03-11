package com.yk.ctrl.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by xbc on 2016/3/23.
 */
public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    /**
     * 将json元素解析为时间类，重写接口的方法
     * @param jsonElement
     * @param type
     * @param jsonDeserializationContext
     * @return
     * @throws JsonParseException
     */
    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type,
                                     JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeEleStr = jsonElement.getAsString();
        if(timeEleStr.length()<19){
            return null;
        }
        String timeStr=timeEleStr.substring(0,19);
        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(timeStr, dtf);
        }finally {
            return dateTime;
        }
    }
}
