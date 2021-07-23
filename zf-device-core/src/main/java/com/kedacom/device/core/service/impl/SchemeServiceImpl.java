package com.kedacom.device.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeConfigResponse;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeQueryResponse;
import com.kedacom.avIntegration.request.scheme.SchemeConfigRequest;
import com.kedacom.avIntegration.request.scheme.SchemeQueryRequest;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.service.SchemeService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: hxj
 * @Date: 2021/5/11 19:39
 */
@Service
@Slf4j
public class SchemeServiceImpl implements SchemeService {

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Value("${zf.msp.server_addr}")
    private String mspUrl;

    private static final String mspPath = "/api/v1/manage/scheme/";

    @Override
    public SchemeConfigResponse config(SchemeConfigRequest request) {
        log.info("预案的画面布局配置入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "config", JSON.toJSONString(request), String.class);
        SchemeConfigResponse configResponse = JSONObject.parseObject(response, SchemeConfigResponse.class);

        log.info("预案的画面布局配置应答:{}", configResponse);
        if (configResponse != null) {
            responseUtil.handleMSPRes("预案的画面布局配置异常:{},{},{}", DeviceErrorEnum.SCHEME_CONFIG_FAILED, configResponse.getError(), null);
        }
        return configResponse;
    }

    @Override
    public SchemeQueryResponse query(SchemeQueryRequest request) {
        log.info("查询预案布局，窗口位置信息入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "query", JSON.toJSONString(request), String.class);
        SchemeQueryResponse queryResponse = JSONObject.parseObject(response, SchemeQueryResponse.class);

        log.info("查询预案布局，窗口位置信息应答:{}", queryResponse);
        if (queryResponse != null) {
            responseUtil.handleMSPRes("查询预案布局，窗口位置信息异常:{},{},{}", DeviceErrorEnum.SCHEME_QUERY_FAILED, queryResponse.getError(), null);
        }
        return queryResponse;
    }


}
