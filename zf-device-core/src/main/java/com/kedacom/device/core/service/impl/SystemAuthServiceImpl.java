package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.acl.network.data.avIntegration.auth.SystemKeepAliveResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLogOutResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLoginResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemVersionResponse;
import com.kedacom.msp.request.RequestBaseParam;
import com.kedacom.msp.request.auth.SystemLoginRequest;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.exception.MspException;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.entity.SystemLoginDTO;
import com.kedacom.device.core.service.SystemAuthService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:12
 */
@Service
@Slf4j
public class SystemAuthServiceImpl implements SystemAuthService {

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Value("${zf.msp.server_addr}")
    private String mspUrl;

    private static final String mspPath = "/api/v1/manage/system/";

    @Override
    public SystemLoginResponse login(SystemLoginRequest request) {
        log.info("登录显控统一服务入参:{}", JSON.toJSONString(request));
        SystemLoginDTO systemLoginDTO = new SystemLoginDTO();
        String umsId = request.getUmsId();
        DeviceInfoEntity entity = deviceMapper.selectById(umsId);
        if (StrUtil.isBlank(entity.getMspAccount()) || StrUtil.isBlank(entity.getMspPassword())) {
            throw new MspException(DeviceErrorEnum.AUTH_ACCOUNT_PASSWORD_FAILED.getCode(), DeviceErrorEnum.AUTH_ACCOUNT_PASSWORD_FAILED.getMsg());
        }
        systemLoginDTO.setUser_name(entity.getMspAccount());
        systemLoginDTO.setPassword(entity.getMspPassword());
        log.info("登录显控统一服务feign接口入参:{}", JSON.toJSONString(systemLoginDTO));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "login", JSON.toJSONString(systemLoginDTO), String.class);
        SystemLoginResponse systemLoginResponse = JSONObject.parseObject(response, SystemLoginResponse.class);

        log.info("登录显控统一服务应答:{}", systemLoginResponse);
        if (systemLoginResponse != null) {
            responseUtil.handleMSPRes("登录显控统一服务异常:{},{},{}", DeviceErrorEnum.SYSTEM_LOGIN_FAILED, systemLoginResponse.getError(), null);
        }
        return systemLoginResponse;
    }

    @Override
    public void keepAlive(RequestBaseParam request) {
        log.info("保活入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "keepalive", JSON.toJSONString(request), String.class);
        SystemKeepAliveResponse aliveResponse = JSONObject.parseObject(response, SystemKeepAliveResponse.class);

        log.info("保活应答:{}", aliveResponse);
        if (aliveResponse != null) {
            responseUtil.handleMSPRes("保活异常:{},{},{}", DeviceErrorEnum.SYSTEM_KEEPALIVE_FAILED, aliveResponse.getError(), null);
        }
    }

    @Override
    public SystemVersionResponse version(RequestBaseParam request) {
        log.info("显控统一服务API版本号入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "version", JSON.toJSONString(request), String.class);
        SystemVersionResponse versionResponse = JSONObject.parseObject(response, SystemVersionResponse.class);

        log.info("显控统一服务API版本号应答:{}", response);
        if (versionResponse != null) {
            responseUtil.handleMSPRes("显控统一服务API版本号异常:{},{},{}", DeviceErrorEnum.SYSTEM_VERSION_FAILED, versionResponse.getError(), null);
        }
        return versionResponse;
    }

    @Override
    public void logout(RequestBaseParam request) {
        log.info("退出显控统一服务入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "logout", JSON.toJSONString(request), String.class);
        SystemLogOutResponse outResponse = JSONObject.parseObject(response, SystemLogOutResponse.class);

        log.info("退出显控统一服务应答:{}", outResponse);
        if (outResponse != null) {
            responseUtil.handleMSPRes("退出显控统一服务异常:{},{},{}", DeviceErrorEnum.SYSTEM_LOGOUT_FAILED, outResponse.getError(), null);
        }
    }


}
