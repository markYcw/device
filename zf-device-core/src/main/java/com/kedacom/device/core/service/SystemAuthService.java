package com.kedacom.device.core.service;

import com.kedacom.msp.request.RequestBaseParam;
import com.kedacom.msp.request.auth.SystemLoginRequest;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLoginResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemVersionResponse;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:12
 */
public interface SystemAuthService {

    SystemLoginResponse login(SystemLoginRequest request);

    void keepAlive(RequestBaseParam request);

    SystemVersionResponse version(RequestBaseParam request);

    void logout(RequestBaseParam request);

}
