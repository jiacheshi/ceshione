package com.yk.ctrl.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Ps on 2019/12/23.
 */
public interface DeviceDataDao {

    //通过mac查询设备数据
    String getDeviceData(@Param("mac")String mac);
}
