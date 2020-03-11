package com.yk.ctrl.service;

import com.yk.ctrl.dao.UserDao;
import com.yk.ctrl.entity.BindDevice;
import com.yk.ctrl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ps on 2019/12/16.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User login(String username,String password){
      return userDao.login(username,password);
    }

    public String checkUserDevices(String username) {
        return userDao.checkUserDevices(username);
    }

    public void bindDeviceToUser(String username, String mac) {
        userDao.bindDeviceToUser(username, mac);
    }

    public void deleteDeviceFromUser(String username, String mac) {
        userDao.deleteDeviceFromUser(username, mac);
    }

    public Integer hintByName(String userName) {
        return userDao.hintByName(userName);
    }

    public void register(User user) {
        userDao.register(user);
    }
}
