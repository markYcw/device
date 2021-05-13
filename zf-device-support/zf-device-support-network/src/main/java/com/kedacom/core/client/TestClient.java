package com.kedacom.core.client;


import com.kedacom.core.anno.*;
import com.kedacom.core.pojo.Teacher;
import com.kedacom.core.pojo.User;

/**
 * @author van.shu
 * @date 2021/5/12 7:49
 */
@KmProxy(name = "test")
public interface TestClient {

    @CommandName
    int addUser(@KmRequestBody User user, @KmRequestParam(name = "DeviceID") String deviceId);

    @CommandName(name = "query")
    boolean getTeacher(@KmHeadParam(name = "ssid") int uid, @KmRequestBody User user, @KmRequestBody Teacher teacher);

}
