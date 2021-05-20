package com.kedacom.device.core.service.impl;

import com.kedacom.acl.network.data.avIntegration.auth.SystemKeepAliveResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLogOutResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLoginResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemVersionResponse;
import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.msp.SystemAuthSdk;
import com.kedacom.device.core.service.SystemAuthService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:12
 */
@Service
@Slf4j
public class SystemAuthServiceImpl implements SystemAuthService {

    @Autowired
    private SystemAuthSdk systemAuthSdk;
    @Autowired
    private HandleResponseUtil responseUtil;

    @Override
    public SystemLoginResponse login(SystemLoginRequest request) {
        log.info("登录显控统一服务入参:{}", request);
        SystemLoginResponse response = systemAuthSdk.login(request);
        log.info("登录显控统一服务应答:{}", response);
        responseUtil.handleMSPRes("登录显控统一服务异常:{},{},{}", DeviceErrorEnum.SYSTEM_LOGIN_FAILED, response.getError(), null);
        return response;
    }

    @Override
    public void keepAlive(RequestBaseParam request) {
        log.info("保活入参:{}", request);
        SystemKeepAliveResponse response = systemAuthSdk.keepAlive(request);
        log.info("保活应答:{}", response);
        responseUtil.handleMSPRes("保活异常:{},{},{}", DeviceErrorEnum.SYSTEM_KEEPALIVE_FAILED, response.getError(), null);
    }

    @Override
    public SystemVersionResponse version(RequestBaseParam request) {
        log.info("显控统一服务API版本号入参:{}", request);
        SystemVersionResponse response = systemAuthSdk.version(request);
        log.info("显控统一服务API版本号应答:{}", response);
        responseUtil.handleMSPRes("显控统一服务API版本号异常:{},{},{}", DeviceErrorEnum.SYSTEM_VERSION_FAILED, response.getError(), null);
        return response;
    }

    @Override
    public void logout(RequestBaseParam request) {
        log.info("退出显控统一服务入参:{}", request);
        SystemLogOutResponse response = systemAuthSdk.logout(request);
        log.info("退出显控统一服务应答:{}", response);
        responseUtil.handleMSPRes("退出显控统一服务异常:{},{},{}", DeviceErrorEnum.SYSTEM_LOGOUT_FAILED, response.getError(), null);
    }


}
