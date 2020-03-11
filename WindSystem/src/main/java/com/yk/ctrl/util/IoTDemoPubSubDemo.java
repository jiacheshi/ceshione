package com.yk.ctrl.util;

import com.yk.ctrl.entity.Adjust;
import com.yk.ctrl.util.AliyunIoTSignUtil;
import org.springframework.stereotype.Service;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.HashMap;
import java.util.Map;

//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
@Service
public class IoTDemoPubSubDemo {

    public static String productKey;
    public static String deviceName;
    public static String deviceSecret;
    public static String regionId = "cn-shanghai";
    // 设置下行数据
    public static String text1;
    public static MqttClient mqttClient;
    // 物模型-属性上报topic3

    // private static String pubTopic = "/" + productKey + "/" + deviceName +
    // "/user/p_data";
    // 自定义topic，在产品Topic列表位置定义
    // private static String subTopic = "/"+productKey + "/" +
    // deviceName+"/user/s_data";

    // 订阅
    public String monitor() {
        // 初始化
        System.out.println("进入monitor" + deviceName);
        initAliyunIoTClient();
        // System.out.println("订阅列表"+subTopic);
//		 try {
//			mqttClient.subscribe("/" +productKey+ "/" + deviceName+ "/user/s_data");
//			} catch (MqttException e) {
//				// TODO Auto-generated catch block
//				System.out.println("订阅失败");
//			}
        // 设置订阅监听
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.out.println("connection Lost");

            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println(mqttClient.getClientId());
                System.err.println(deviceName);
                System.err.println("匿名" + productKey);
                System.err.println("Topic : " + s);
                text1 = new String(mqttMessage.getPayload());
                System.err.println("打印输出消息payLoad" + new String(mqttMessage.getPayload())); // 打印输出消息payLoad
                // System.out.println("close"+mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        return text1;
    }

    // 上报数值方法
    public void begin(Adjust a, String productKey, String deviceName) {
        // 汇报属性
        postDeviceProperties(a, productKey, deviceName);
        try {
            mqttClient.subscribe("/" + productKey + "/" + deviceName + "/user/s_data");
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // IoTDemoPubSubDemo.mqttClient.close();
        } // 订阅Topic
        // 返回下行数据

    }
//
//    // 监听方法，返回json字符串
//

    /**
     * 初始化 Client 对象
     */
    private static void initAliyunIoTClient() {
        System.out.println("进入initAliyunIoTClient()" + deviceName);

        try {
            // 构造连接需要的参数
            String clientId = "java" + System.currentTimeMillis();
            Map<String, String> params = new HashMap<>(16);
            params.put("productKey", productKey);
            params.put("deviceName", deviceName);

            System.err.println("初始化三元" + deviceName);

            params.put("clientId", clientId);
            String timestamp = String.valueOf(System.currentTimeMillis());
            params.put("timestamp", timestamp);
            // cn-shanghai
            String targetServer = "tcp://" + productKey + ".iot-as-mqtt." + regionId + ".aliyuncs.com:1883";

            System.out.println("targetServer" + targetServer);
            String mqttclientId = clientId + "|securemode=3,signmethod=hmacsha1,timestamp=" + timestamp + "|";
            String mqttUsername = deviceName + "&" + productKey;
            String mqttPassword = AliyunIoTSignUtil.sign(params, deviceSecret, "hmacsha1");

            connectMqtt(targetServer, mqttclientId, mqttUsername, mqttPassword);

            // 订阅TOP

        } catch (Exception e) {
            System.out.println("initAliyunIoTClient error " + e.getMessage());

        }
    }

    public static void connectMqtt(String url, String clientId, String mqttUsername, String mqttPassword)
            throws Exception {
        System.out.println("进入connectMqtt" + deviceName);
        MemoryPersistence persistence = new MemoryPersistence();
        mqttClient = new MqttClient(url, clientId, persistence);

        MqttConnectOptions connOpts = new MqttConnectOptions();
        // MQTT 3.1.1
        connOpts.setMqttVersion(4);// 版本
        connOpts.setAutomaticReconnect(false);// false不自动链接
        connOpts.setCleanSession(false);// true不会跨维护状态
        connOpts.setUserName(mqttUsername);
        connOpts.setPassword(mqttPassword.toCharArray());
        connOpts.setKeepAliveInterval(60);//心跳包
        mqttClient.connect(connOpts);
        System.out.println("连接对象" + mqttClient.toString());
    }

    /**
     * 汇报属性
     */
    private static void postDeviceProperties(Adjust a, String productKey, String deviceName) {

        try {
            // 上报数据
            // 高级版 物模型-属性上报payload
            System.out.println("上报属性值");
            // String payloadJson = "{\"params\":{\"Status\":0,\"FAN\":\""+data+"\"}}";

            String payloadJson = a.getName() + "=" + a.getDataStr();
            MqttMessage message = new MqttMessage(payloadJson.getBytes("utf-8"));
            message.setQos(0);
            System.err.println(deviceName);
            mqttClient.publish("/" + productKey + "/" + deviceName + "/user/p_data", message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

//    public static void clean() {
//        mqttClient = null;
//    }

}
