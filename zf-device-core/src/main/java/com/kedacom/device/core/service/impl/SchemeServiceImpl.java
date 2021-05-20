package com.kedacom.device.core.service.impl;

import com.kedacom.acl.network.data.avIntegration.scheme.SchemeConfigResponse;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeQueryResponse;
import com.kedacom.avIntegration.request.scheme.SchemeConfigRequest;
import com.kedacom.avIntegration.request.scheme.SchemeQueryRequest;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.msp.SchemeManageSdk;
import com.kedacom.device.core.service.SchemeService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: hxj
 * @Date: 2021/5/11 19:39
 */
@Service
@Slf4j
public class SchemeServiceImpl implements SchemeService {

    @Autowired
    private SchemeManageSdk schemeManageSdk;
    @Autowired
    private HandleResponseUtil responseUtil;

    @Override
    public SchemeConfigResponse config(SchemeConfigRequest request) {
        log.info("预案的画面布局配置入参:{}", request);
        SchemeConfigResponse response = schemeManageSdk.config(request);
        log.info("预案的画面布局配置应答:{}", response);
        responseUtil.handleMSPRes("预案的画面布局配置异常:{},{},{}", DeviceErrorEnum.SCHEME_CONFIG_FAILED, response.getError(), null);
        return response;
    }

    @Override
    public SchemeQueryResponse query(SchemeQueryRequest request) {
        log.info("查询预案布局，窗口位置信息入参:{}", request);
        SchemeQueryResponse response = schemeManageSdk.query(request);
        log.info("查询预案布局，窗口位置信息应答:{}", response);
        responseUtil.handleMSPRes("查询预案布局，窗口位置信息异常:{},{},{}", DeviceErrorEnum.SCHEME_QUERY_FAILED, response.getError(), null);
        return response;
    }


}
