package com.yk.ctrl.service;

import com.yk.ctrl.dao.DeviceDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ps on 2019/12/23.
 */
@Service
public class DeviceDataService {
    @Autowired
    DeviceDataDao dataDao;

    public String getDeviceData(String mac) {
        return dataDao.getDeviceData(mac);
    }
}
