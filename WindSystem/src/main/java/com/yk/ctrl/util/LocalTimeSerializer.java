package com.yk.ctrl.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Ps on 2019/8/28.
 */
public class LocalTimeSerializer implements JsonSerializer<LocalTime> {

    @Override
    public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext jsonSerializationContext) {
        if(localTime == null){
            return new JsonPrimitive("");
        }


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        return new JsonPrimitive(localTime.format(dtf));
    }
}
