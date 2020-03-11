package com.yk.ctrl.web.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import com.yk.ctrl.entity.User;
import com.yk.ctrl.service.UserService;
import com.yk.ctrl.entity.ServiceResponse;
import org.apache.logging.log4j.LogManager;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.Logger;


/**
 * Created by Ps on 2019/12/16.
 */
@RestController
@RequestMapping("/login")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public ServiceResponse phoneLogin(@RequestBody ObjectNode objectNode) {
        logger.info("Receive a new phoneLogin request : " + objectNode.toString());
        ServiceResponse serviceResponse = new ServiceResponse();
        User user = null;
        try {
            if (!objectNode.has("username")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        ("参数缺少键值，username,请检查参数").getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            if (!objectNode.has("password")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：password，请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            String username = objectNode.get("username").asText();
            String password = objectNode.get("password").asText();
            user = userService.login(username, password);
            logger.info(user);
            if (user == null) {
                serviceResponse.setErrorCode(ServiceResponse.NO_USER);
                serviceResponse.setMessage(new String("查无此用户".getBytes("UTF-8"), "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
            serviceResponse.setMessage(new String("登录成功".getBytes("UTF-8"), "UTF-8"));
            serviceResponse.setResultNode(user);
            logger.info(serviceResponse);
            return serviceResponse;
        } catch (Exception e) {
            e.printStackTrace();
            serviceResponse.setErrorCode(ServiceResponse.ERROR);
            serviceResponse.setMessage("System Error");
            serviceResponse.setResultNode(null);
            return serviceResponse;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ServiceResponse register(@RequestBody ObjectNode objectNode) {
        logger.info("Receive a new register request : " + objectNode.toString());
        ServiceResponse serviceResponse = new ServiceResponse();
        try {
            if (!objectNode.has("username")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        ("参数缺少键值，username,请检查参数").getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            if (!objectNode.has("password")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：password，请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            if (!objectNode.has("phoneNumber")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：phoneNumber，请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            if (!objectNode.has("email")) {
                serviceResponse.setErrorCode(ServiceResponse.CMD_PARAS_ERROR);
                serviceResponse.setMessage(new String(
                        "参数缺少键值：email，请检查参数".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            String username = objectNode.get("username").asText();
            String password = objectNode.get("password").asText();
            String phoneNum = objectNode.get("phoneNumber").asText();
            String email = objectNode.get("email").asText();
            Integer isExist = userService.hintByName(username);
            logger.info(isExist);
            if (isExist != null) {
                logger.info("There is a same name in the database.");
                serviceResponse.setErrorCode(ServiceResponse.REGISTER_ERROR);
                serviceResponse.setMessage(new String(
                        "用户名已存在，请检查".getBytes("UTF-8"),
                        "UTF-8"));
                serviceResponse.setResultNode(null);
                return serviceResponse;
            }
            User user = new User();
            user.setEmail(email);
            user.setPhoneNumber(phoneNum);
            user.setuName(username);
            user.setuPass(password);
            userService.register(user);
            serviceResponse.setErrorCode(ServiceResponse.NO_ERROR_CODE);
            serviceResponse.setMessage(new String("注册成功".getBytes("UTF-8"), "UTF-8"));
            serviceResponse.setResultNode(user);
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

}
