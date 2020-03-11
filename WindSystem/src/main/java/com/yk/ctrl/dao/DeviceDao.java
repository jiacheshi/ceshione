package com.yk.ctrl.dao;

import com.yk.ctrl.entity.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Ps on 2019/12/16.
 */
public interface DeviceDao {

    //查找mac地址的设备已绑定的用户名列表
    String deviceBindedUser(@Param("mac")String mac);

    //把user绑定到mac设备上
    void bindUserToDevice(@Param("username")String username, @Param("nickname")String nickname,
                          @Param("password")String password,@Param("mac")String mac);

    //通过mac查询设备信息
    Device selByMac(String mac);

    //在设备中删除用户
    void deleteUserFromDevice(@Param("username")String username, @Param("mac")String mac);

    String getNicknameByMac( @Param("mac")String mac);
}
