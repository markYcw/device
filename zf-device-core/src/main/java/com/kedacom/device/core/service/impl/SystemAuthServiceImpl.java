package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.kedacom.acl.network.data.avIntegration.auth.SystemKeepAliveResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLogOutResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLoginResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemVersionResponse;
import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.device.common.exception.AuthException;
import com.kedacom.device.core.config.AvIntegrationErrCode;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.msp.SystemAuthSdk;
import com.kedacom.device.core.service.SystemAuthService;
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
    private AvIntegrationErrCode avIntegrationErrCode;

    @Override
    public SystemLoginResponse login(SystemLoginRequest request) {
        log.info("登录显控统一服务入参:{}", request);
        SystemLoginResponse response = systemAuthSdk.login(request);
        log.info("登录显控统一服务应答:{}", response);
        handleRes("登录显控统一服务异常:{},{},{}", response.getError(), null);
        return response;
    }

    @Override
    public void keepAlive(RequestBaseParam request) {
        log.info("保活入参:{}", request);
        SystemKeepAliveResponse response = systemAuthSdk.keepAlive(request);
        log.info("保活应答:{}", response);
        handleRes("保活异常:{},{},{}", response.getError(), null);
    }

    @Override
    public SystemVersionResponse version(RequestBaseParam request) {
        log.info("显控统一服务API版本号入参:{}", request);
        SystemVersionResponse response = systemAuthSdk.version(request);
        log.info("显控统一服务API版本号应答:{}", response);
        handleRes("显控统一服务API版本号异常:{},{},{}", response.getError(), null);
        return response;
    }

    @Override
    public void logout(RequestBaseParam request) {
        log.info("退出显控统一服务入参:{}", request);
        SystemLogOutResponse response = systemAuthSdk.logout(request);
        log.info("退出显控统一服务应答:{}", response);
        handleRes("退出显控统一服务异常:{},{},{}", response.getError(), null);
    }
    
    private void handleRes(String str, Integer errCode, String errorMsg) {
        if (errCode != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(errorMsg)) {
                log.error(str, errCode, DeviceErrorEnum.SYSTEM_AUTH_FAILED.getCode(), errorMsg);
                throw new AuthException(DeviceErrorEnum.SYSTEM_AUTH_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(errCode));
            } else if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(errCode))) {
                log.error(str, errCode, DeviceErrorEnum.SYSTEM_AUTH_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(errCode));
                throw new AuthException(DeviceErrorEnum.SYSTEM_AUTH_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(errCode));
            } else {
                log.error(str, errCode, DeviceErrorEnum.SYSTEM_AUTH_FAILED.getCode(), DeviceErrorEnum.SYSTEM_AUTH_FAILED.getMsg());
                throw new AuthException(DeviceErrorEnum.SYSTEM_AUTH_FAILED.getCode(), DeviceErrorEnum.SYSTEM_AUTH_FAILED.getMsg());
            }
        }
    }

}
