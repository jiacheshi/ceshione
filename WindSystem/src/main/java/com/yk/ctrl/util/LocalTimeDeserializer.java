package com.yk.ctrl.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Ps on 2019/8/28.
 */
public class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {
    /**
     * 将json元素解析为时间类，重写接口的方法
     * @param jsonElement
     * @param type
     * @param jsonDeserializationContext
     * @return
     * @throws JsonParseException
     */
    @Override
    public LocalTime deserialize(JsonElement jsonElement, Type type,
                                     JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        String timeEleStr = jsonElement.getAsString();
        if(timeEleStr.length()<5){
            return null;
        }
        String timeStr=timeEleStr.substring(0,5);

        LocalTime dateTime = null;
        try {
            dateTime = LocalTime.parse(timeStr, dtf);
        }finally {
            return dateTime;
        }
    }
}
