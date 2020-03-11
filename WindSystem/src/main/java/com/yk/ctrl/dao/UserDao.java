package com.yk.ctrl.dao;

import com.yk.ctrl.entity.BindDevice;
import com.yk.ctrl.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Ps on 2019/12/16.
 */
public interface UserDao {


    User login(@Param("username")String username, @Param("password")String password);


    //查询用户名是否重复
    Integer hintByName(@Param("username")String userName);

    //注册用户
    void register(@Param("userInfo")User userInfo);

    String checkUserDevices(@Param("username")String username);

    //把mac设备绑定到user上
    void bindDeviceToUser(@Param("username")String username,@Param("mac") String mac);

    //在用户中删除设备
    void deleteDeviceFromUser(@Param("username")String username, @Param("mac")String mac);
}
