package com.kedacom.device.core.service.impl;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.avIntegration.response.auth.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.auth.SystemLogOutResponse;
import com.kedacom.avIntegration.response.auth.SystemLoginResponse;
import com.kedacom.avIntegration.response.auth.SystemVersionResponse;
import com.kedacom.device.common.exception.AuthException;
import com.kedacom.device.core.data.DeviceConstants;
import com.kedacom.device.core.data.DeviceErrorEnum;
import com.kedacom.device.core.msp.SystemAuthSdk;
import com.kedacom.device.core.service.SystemAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:12
 */
@Service
@Slf4j
public class SystemAuthServiceImpl implements SystemAuthService {

    @Resource
    private SystemAuthSdk systemAuthSdk;

    @Override
    public SystemLoginResponse login(SystemLoginRequest request) {
        log.info("登录显控统一服务入参:{}", request);
        SystemLoginResponse response = systemAuthSdk.login(request);
        log.info("登录显控统一服务应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new AuthException(DeviceErrorEnum.SYSTEM_LOGIN_FAILED.getCode(), DeviceErrorEnum.SYSTEM_LOGIN_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public SystemKeepAliveResponse keepAlive(RequestBaseParam request) {
        log.info("保活入参:{}", request);
        SystemKeepAliveResponse response = systemAuthSdk.keepAlive(request);
        log.info("保活应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new AuthException(DeviceErrorEnum.SYSTEM_KEEPALIVE_FAILED.getCode(), DeviceErrorEnum.SYSTEM_KEEPALIVE_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public SystemVersionResponse version(RequestBaseParam request) {
        log.info("显控统一服务API版本号入参:{}", request);
        SystemVersionResponse response = systemAuthSdk.version(request);
        log.info("显控统一服务API版本号应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new AuthException(DeviceErrorEnum.SYSTEM_VERSION_FAILED.getCode(), DeviceErrorEnum.SYSTEM_VERSION_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public SystemLogOutResponse logout(RequestBaseParam request) {
        log.info("退出显控统一服务入参:{}", request);
        SystemLogOutResponse response = systemAuthSdk.logout(request);
        log.info("退出显控统一服务应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new AuthException(DeviceErrorEnum.SYSTEM_LOGOUT_FAILED.getCode(), DeviceErrorEnum.SYSTEM_LOGOUT_FAILED.getMsg());
        }
        return response;
    }


}
