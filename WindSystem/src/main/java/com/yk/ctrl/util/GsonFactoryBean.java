package com.yk.ctrl.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yk.ctrl.util.LocalDateTimeDeserializer;
import com.yk.ctrl.util.LocalDateTimeSerializer;
import org.springframework.beans.factory.FactoryBean;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by xbc on 2018/5/8.
 */
public class GsonFactoryBean implements FactoryBean<Gson> {
    @Override
    public Gson getObject() throws Exception {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
                .registerTypeHierarchyAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .registerTypeHierarchyAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeHierarchyAdapter(LocalTime.class,
                        new LocalTimeDeserializer())
                .registerTypeHierarchyAdapter(LocalTime.class,
                        new LocalTimeSerializer())
//                .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式
                .create();
    }

    @Override
    public Class<?> getObjectType() {
        return Gson.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
