package com.kedacom.device.core.service;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.avIntegration.response.auth.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.auth.SystemLogOutResponse;
import com.kedacom.avIntegration.response.auth.SystemLoginResponse;
import com.kedacom.avIntegration.response.auth.SystemVersionResponse;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:12
 */
public interface SystemAuthService {

    SystemLoginResponse login(SystemLoginRequest request);

    SystemKeepAliveResponse keepAlive(RequestBaseParam request);

    SystemVersionResponse version(RequestBaseParam request);

    SystemLogOutResponse logout(RequestBaseParam request);

}
