package com.yk.ctrl.web.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yk.ctrl.entity.Device;
import com.yk.ctrl.entity.DeviceData;
import com.yk.ctrl.entity.ServiceResponse;
import com.yk.ctrl.service.DeviceDataService;
import com.yk.ctrl.util.LocalDateTimeDeserializer;
import com.yk.ctrl.util.LocalDateTimeSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by Ps on 2019/12/23.
 */
@RestController
@RequestMapping("/deviceData")
public class DeviceDataController {

    private static final Logger logger = LogManager.getLogger(UserController.class.getName());
    private static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
            .registerTypeHierarchyAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeHierarchyAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
            .create();
    @Autowired
    DeviceDataService dataService;


    @RequestMapping(value = "/getData",method = RequestMethod.POST)
    public ServiceResponse getData(@RequestBody ObjectNode objectNode){
        logger.info("Receive a new request to get data of : "+objectNode.toString());
        ServiceResponse serviceResponse = new ServiceResponse();
        DeviceData deviceData = null;
        try{
            if (!objectNode.has("mac")){
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：mac,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                logger.info(serviceResponse);
                return serviceResponse;
            }
            String mac = objectNode.get("mac").asText();
            String data = dataService.getDeviceData(mac);
            if (data == null){
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "暂无数据".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                logger.info(serviceResponse);
                return serviceResponse;
            }
            if (!data.isEmpty()){
                logger.info(data);
                deviceData = gson.fromJson(data, DeviceData.class);
                logger.info(deviceData.toString());
                serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
                serviceResponse.setMessage(null);
                serviceResponse.setResultNode(deviceData);
                return serviceResponse;
            }
            return serviceResponse;
        }catch (Exception e){
            e.printStackTrace();
            serviceResponse.setErrorCode(ServiceResponse.ERROR);
            serviceResponse.setMessage(null);
            serviceResponse.setResultNode(null);
            return serviceResponse;
        }
    }



}
