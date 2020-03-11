package com.yk.ctrl.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yk.ctrl.entity.*;
import com.yk.ctrl.service.DeviceDataService;
import com.yk.ctrl.service.DeviceService;
import com.yk.ctrl.util.IoTDemoPubSubDemo;
import com.yk.ctrl.service.UserService;
import com.yk.ctrl.util.LocalDateTimeDeserializer;
import com.yk.ctrl.util.LocalDateTimeSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ps on 2019/12/16.
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    private static final Logger logger = LogManager.getLogger(UserController.class.getName());

    @Autowired
    DeviceService deviceService;
    @Autowired
    UserService userService;
    @Autowired
    DeviceDataService dataService;
    @Autowired
    IoTDemoPubSubDemo ioTDemoPubSubDemo;

    private static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeHierarchyAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeHierarchyAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
            .create();
//绑定功能接口测试通过
    @RequestMapping(value = "/bindDevice", method = RequestMethod.POST)
    public ServiceResponse bindDevice(@RequestBody ObjectNode objectNode) {
        logger.info("Receive a request to bind device:" + objectNode.toString());
        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            if (!objectNode.has("username")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：username,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                logger.info(serviceResponse);
                return serviceResponse;
            }
            if (!objectNode.has("nickName")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：nickName,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                logger.info(serviceResponse);
                return serviceResponse;
            }
            if (!objectNode.has("mac")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：mac,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                logger.info(serviceResponse);
                return serviceResponse;
            }
            if (!objectNode.has("password")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：password,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                logger.info(serviceResponse);
                return serviceResponse;
            }

            String username = objectNode.get("username").asText();
            String nickName = objectNode.get("nickName").asText();
            String mac = objectNode.get("mac").asText();
            String password = objectNode.get("password").asText();
            //检查该用户绑定设备是否到达上限
            String userDeviceStr = userService.checkUserDevices(username);
            List<String> userDeviceList = new ArrayList<>();
            if (userDeviceStr != null) {
                String[] userDevices = userDeviceStr.split(",");
                userDeviceList = Arrays.asList(userDevices);
            }
            if (userDeviceList != null && userDeviceList.size() >= 5) {
                serviceResponse.setErrorCode(ServiceResponse.BIND_ERROR_DEVICE);
                serviceResponse.setMessage(new String(
                        ("用户绑定设备已达上限值").getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                logger.info(serviceResponse);
                return serviceResponse;
            }
            //检查该设备绑定用户是否到达上限
            String deviceUserStr = deviceService.deviceBindedUser(mac);
            List<String> deviceUserList = new ArrayList<>();
            if (deviceUserStr != null) {
                String[] deviceUsers = deviceUserStr.split(",");
                deviceUserList = Arrays.asList(deviceUsers);
            }
            if (deviceUserList != null && deviceUserList.size() >= 5) {
                serviceResponse.setErrorCode(ServiceResponse.BIND_ERROR_USER);
                serviceResponse.setMessage(new String(
                        ("设备绑定用户已达上限值").getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                logger.info(serviceResponse);
                return serviceResponse;
            }
            //检查该设备该用户是否绑定过
            if (userDeviceList.contains(mac)) {
                serviceResponse.setErrorCode(ServiceResponse.BIND_ERROR_HAVE);
                serviceResponse.setMessage(new String(
                        ("该设备已被此用户绑定").getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                logger.info(serviceResponse);
                return serviceResponse;
            }
            userService.bindDeviceToUser(username, mac);
            deviceService.bindUserToDevice(username, nickName, password, mac);
            serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
            serviceResponse.setMessage(new String(
                    ("绑定成功").getBytes("UTF-8"),
                    "UTF-8"));
            serviceResponse.setResultNode(null);
            logger.info(serviceResponse);
            return serviceResponse;
        } catch (Exception e) {
            e.printStackTrace();
            serviceResponse.setErrorCode(ServiceResponse.ERROR);
            serviceResponse.setMessage(null);
            serviceResponse.setResultNode(null);
            return serviceResponse;
        }
    }
//删除设备功能正常
    @RequestMapping(value = "/deleteDevice", method = RequestMethod.POST)
    public ServiceResponse deleteDevice(@RequestBody ObjectNode objectNode) {
        logger.info("Receive a request to delete device : " + objectNode.toString());
        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            if (!objectNode.has("username")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：username,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            if (!objectNode.has("mac")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：mac,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            String username = objectNode.get("username").asText();
            String mac = objectNode.get("mac").asText();
            userService.deleteDeviceFromUser(username, mac);
            deviceService.deleteUserFromDevice(username, mac);
            serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
            serviceResponse.setMessage(new String(
                    ("设备解绑成功").getBytes("UTF-8"),
                    "UTF-8"));
            serviceResponse.setResultNode(null);
            return serviceResponse;
        } catch (Exception e) {
            e.printStackTrace();
            serviceResponse.setErrorCode(ServiceResponse.ERROR);
            serviceResponse.setMessage(null);
            serviceResponse.setResultNode(null);
            return serviceResponse;
        }
    }
//查询用户绑定设备
    @RequestMapping(value = "/getUserDevice", method = RequestMethod.POST)
    public ServiceResponse getUserDevice(@RequestBody ObjectNode objectNode) {
        logger.info("Receive a request to get all device that user has binded : " + objectNode);
        ServiceResponse serviceResponse = new ServiceResponse();
        DeviceData deviceData = null;
        try {
            if (!objectNode.has("username")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：username,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            String username = objectNode.get("username").asText();
            String userDeviceStr = userService.checkUserDevices(username);
            List<String> userDeviceList = new ArrayList<>();
            if (userDeviceStr != null) {
                String[] userDevices = userDeviceStr.split(",");
                userDeviceList = Arrays.asList(userDevices);
            }
            if (userDeviceList.size() > 0) {
                List<BindDevice> bindDevices = new ArrayList<>();
                for (int i = 0; i < userDeviceList.size(); i++) {
                    String mac = userDeviceList.get(i);
                    String nickname = deviceService.getNicknameByMac(mac);
                    if (nickname == null) {
                        nickname = mac;
                    }
                    String data = dataService.getDeviceData(mac);
                    logger.info(data);
                    if (data == null){
                        deviceData = null;
                    }else if (!data.isEmpty()) {
                        deviceData = gson.fromJson(data, DeviceData.class);
                        logger.info(deviceData.toString());
                    }
                    BindDevice device = new BindDevice();
                    device.setMac(mac);
                    device.setNickname(nickname);
                    device.setDeviceData(deviceData);
                    bindDevices.add(device);
                }
                serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
                serviceResponse.setMessage(new String(
                        "查询成功".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(bindDevices);
                return serviceResponse;
            }
            if (userDeviceList.size() == 0) {
                serviceResponse.setErrorCode(ServiceResponse.BIND_DEVICE_NULL);
                serviceResponse.setMessage(new String(
                        "暂无设备".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
            serviceResponse.setMessage(new String(
                    "查询成功".getBytes("UTF-8"),
                    "UTF-8"));
            serviceResponse.setResultNode(null);
            return serviceResponse;
        } catch (Exception e) {
            e.printStackTrace();
            serviceResponse.setErrorCode(ServiceResponse.ERROR);
            serviceResponse.setMessage(null);
            serviceResponse.setResultNode(null);
            return serviceResponse;
        }
    }
    //调节风速
    @RequestMapping(value = "/speedControl", method = RequestMethod.POST)
    public ServiceResponse speedControl(@RequestBody ObjectNode objectNode) {
        logger.info("Receive a request to control the wind speed: " + objectNode.toString());
        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            if (!objectNode.has("mac")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：mac,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            if (!objectNode.has("cmd")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：cmd,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            String mac = objectNode.get("mac").asText();
            String cmd = objectNode.get("cmd").asText();
            Device device = new Device();
            device = deviceService.selByMac(mac);
            if (device == null) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "查询不到当前设备".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            Adjust adjust = new Adjust();
            String json = ioTDemoPubSubDemo.monitor();
            adjust.setName("FAN");
            adjust.setDataStr(cmd);
            logger.info("There is a cmd to "+ mac +"  "+ adjust.toString());
            ioTDemoPubSubDemo.begin(adjust, device.getProductKey(), device.getDeviceName());
            serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
            serviceResponse.setMessage(new String(
                    "参数设置成功".getBytes("UTF-8"),
                    "UTF-8"));
            serviceResponse.setResultNode(null);
            return serviceResponse;
        } catch (Exception e) {
            e.printStackTrace();
            serviceResponse.setErrorCode(ServiceResponse.ERROR);
            serviceResponse.setMessage(null);
            serviceResponse.setResultNode(null);
            return serviceResponse;
        }

    }
//调节工作模式
    @RequestMapping(value = "/modeControl", method = RequestMethod.POST)
    public ServiceResponse modeControl(@RequestBody ObjectNode objectNode) {
        logger.info("Receive a request to control the mode: " + objectNode.toString());
        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            if (!objectNode.has("mac")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：mac,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            if (!objectNode.has("cmd")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：cmd,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            String mac = objectNode.get("mac").asText();
            String cmd = objectNode.get("cmd").asText();
            Device device = new Device();
            device = deviceService.selByMac(mac);
            if (device == null) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "查询不到当前设备".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            Adjust adjust = new Adjust();
            String json = ioTDemoPubSubDemo.monitor();
            adjust.setName("MODE");
            adjust.setDataStr(cmd);
            logger.info("There is a cmd to "+ mac +"  "+ adjust.toString());

            ioTDemoPubSubDemo.begin(adjust, device.getProductKey(), device.getDeviceName());
            serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
            serviceResponse.setMessage(new String(
                    "参数设置成功".getBytes("UTF-8"),
                    "UTF-8"));
            serviceResponse.setResultNode(null);
            return serviceResponse;
        } catch (Exception e) {
            e.printStackTrace();
            serviceResponse.setErrorCode(ServiceResponse.ERROR);
            serviceResponse.setMessage(null);
            serviceResponse.setResultNode(null);
            return serviceResponse;
        }

    }
//调节新风比例
    @RequestMapping(value = "/scaleControl", method = RequestMethod.POST)
    public ServiceResponse scaleControl(@RequestBody ObjectNode objectNode) {
        logger.info("Receive a request to control the scale: " + objectNode.toString());
        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            if (!objectNode.has("mac")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：mac,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            if (!objectNode.has("cmd")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：cmd,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            String mac = objectNode.get("mac").asText();
            String cmd = objectNode.get("cmd").asText();
            Device device = new Device();
            device = deviceService.selByMac(mac);
            System.out.println(mac);
            if (device == null) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "查询不到当前设备".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            Adjust adjust = new Adjust();
            String json = ioTDemoPubSubDemo.monitor();
            adjust.setName("SCALE");
            adjust.setDataStr(cmd);
            logger.info("There is a cmd to "+ mac +"  "+ adjust.toString());
            ioTDemoPubSubDemo.begin(adjust, device.getProductKey(), device.getDeviceName());
            serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
            serviceResponse.setMessage(new String(
                    "参数设置成功".getBytes("UTF-8"),
                    "UTF-8"));
            serviceResponse.setResultNode(null);
            return serviceResponse;
        } catch (Exception e) {
            e.printStackTrace();
            serviceResponse.setErrorCode(ServiceResponse.ERROR);
            serviceResponse.setMessage(null);
            serviceResponse.setResultNode(null);
            return serviceResponse;
        }

    }
//切换设备时调用
    @RequestMapping(value = "/switchDevice", method = RequestMethod.POST)
    public ServiceResponse switchDevice(@RequestBody ObjectNode objectNode) {
        logger.info("Receive a new requeset to switch the device: " + objectNode.toString());
        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            if (!objectNode.has("mac")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：mac,请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            String mac = objectNode.get("mac").asText();
            Device device = deviceService.selByMac(mac);
            if (device == null) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "查询不到当前设备".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            ioTDemoPubSubDemo.deviceName = device.getDeviceName();
            ioTDemoPubSubDemo.deviceSecret = device.getDeviceSecret();
            ioTDemoPubSubDemo.productKey = device.getProductKey();
            String JOSN = ioTDemoPubSubDemo.monitor();
            serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
            serviceResponse.setMessage(new String(
                    "参数设置成功".getBytes("UTF-8"),
                    "UTF-8"));
            serviceResponse.setResultNode(null);
            return serviceResponse;
        } catch (Exception e) {
            e.printStackTrace();
            serviceResponse.setErrorCode(ServiceResponse.ERROR);
            serviceResponse.setMessage(null);
            serviceResponse.setResultNode(null);
            return serviceResponse;
        }
    }


}
