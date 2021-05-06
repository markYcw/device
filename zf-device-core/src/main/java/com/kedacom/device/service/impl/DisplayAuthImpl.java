package com.kedacom.device.service.impl;

import com.kedacom.BaseResult;
import com.kedacom.acl.network.unite.AvIntegrationInterface;
import com.kedacom.avIntegration.request.DisplayControlBaseDTO;
import com.kedacom.avIntegration.request.DisplayControlLoginRequest;
import com.kedacom.avIntegration.response.DisplayControlLoginResponse;
import com.kedacom.avIntegration.response.DisplayControlVersionResponse;
import com.kedacom.device.service.DisplayAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:34
 */
@Slf4j
@Service
public class DisplayAuthImpl implements DisplayAuthService {

    @Resource
    private AvIntegrationInterface avIntegrationInterface;


    @Override
    public BaseResult<DisplayControlLoginResponse> login(DisplayControlLoginRequest request) {
        log.info("登录显控统一服务入参--DisplayControlLoginRequest:{}", request);
        BaseResult<DisplayControlLoginResponse> response = avIntegrationInterface.login(request);
        log.info("登录显控统一服务应答--DisplayControlLoginResponse:{}", response);
        return response;
    }

    @Override
    public BaseResult keepalive(DisplayControlBaseDTO baseDTO) {
        log.info("显控保活入参--baseDTO:{}", baseDTO);
        BaseResult baseResult = avIntegrationInterface.keepalive(baseDTO);
        log.info("显控保活应答--baseResult:{}", baseResult);
        return baseResult;
    }

    @Override
    public BaseResult<DisplayControlVersionResponse> version(DisplayControlBaseDTO baseDTO) {
        log.info("显控获取版本入参--baseDTO:{}", baseDTO);
        BaseResult<DisplayControlVersionResponse> baseResult = avIntegrationInterface.version(baseDTO);
        log.info("显控获取版本应答--DisplayControlVersionResponse:{}", baseResult);
        return baseResult;
    }

    @Override
    public BaseResult logout(DisplayControlBaseDTO baseDTO) {
        log.info("显控登出入参--baseDTO:{}", baseDTO);
        BaseResult baseResult = avIntegrationInterface.logout(baseDTO);
        log.info("显控登出应答--baseResult:{}", baseResult);
        return baseResult;
    }

}
