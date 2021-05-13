package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.kedacom.avIntegration.request.scheme.SchemeConfigRequest;
import com.kedacom.avIntegration.request.scheme.SchemeQueryRequest;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeConfigResponse;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeQueryResponse;
import com.kedacom.device.core.config.AvIntegrationErrCode;
import com.kedacom.device.core.data.DeviceConstants;
import com.kedacom.device.core.data.DeviceErrorEnum;
import com.kedacom.device.core.exception.TvWallException;
import com.kedacom.device.core.msp.SchemeManageSdk;
import com.kedacom.device.core.service.SchemeService;
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
    private AvIntegrationErrCode avIntegrationErrCode;

    @Override
    public SchemeConfigResponse config(SchemeConfigRequest request) {
        log.info("预案的画面布局配置入参:{}", request);
        SchemeConfigResponse response = schemeManageSdk.config(request);
        log.info("预案的画面布局配置应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(response.getError()))) {
                throw new TvWallException(DeviceErrorEnum.SCHEME_CONFIG_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
            } else {
                throw new TvWallException(DeviceErrorEnum.SCHEME_CONFIG_FAILED.getCode(), DeviceErrorEnum.SCHEME_CONFIG_FAILED.getMsg());
            }
        }
        return response;
    }

    @Override
    public SchemeQueryResponse query(SchemeQueryRequest request) {
        log.info("查询预案布局，窗口位置信息入参:{}", request);
        SchemeQueryResponse response = schemeManageSdk.query(request);
        log.info("查询预案布局，窗口位置信息应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(response.getError()))) {
                throw new TvWallException(DeviceErrorEnum.SCHEME_QUERY_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
            } else {
                throw new TvWallException(DeviceErrorEnum.SCHEME_QUERY_FAILED.getCode(), DeviceErrorEnum.SCHEME_QUERY_FAILED.getMsg());
            }
        }
        return response;
    }
}
