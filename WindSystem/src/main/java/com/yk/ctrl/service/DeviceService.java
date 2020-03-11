package com.yk.ctrl.service;

import com.yk.ctrl.dao.DeviceDao;
import com.yk.ctrl.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ps on 2019/12/16.
 */
@Service
public class DeviceService {
    @Autowired
    DeviceDao deviceDao;

    public void bindUserToDevice(String username, String nickname,String password,String mac) {
        deviceDao.bindUserToDevice(username, nickname,password,mac);
    }

    public String deviceBindedUser(String mac) {
        return deviceDao.deviceBindedUser(mac);
    }

    public Device selByMac(String mac) {
        return deviceDao.selByMac(mac);
    }

    public void deleteUserFromDevice(String username, String mac) {
        deviceDao.deleteUserFromDevice(username, mac);
    }

    public String getNicknameByMac(String mac) {
        return deviceDao.getNicknameByMac(mac);
    }
}
