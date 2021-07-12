package com.kedacom.device.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.acl.network.data.avIntegration.decoder.OsdConfigResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.OsdDeleteResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.StyleConfigResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.StyleQueryResponse;
import com.kedacom.avIntegration.request.decoder.OsdConfigRequest;
import com.kedacom.avIntegration.request.decoder.OsdDeleteRequest;
import com.kedacom.avIntegration.request.decoder.StyleConfigRequest;
import com.kedacom.avIntegration.request.decoder.StyleQueryRequest;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.msp.DecoderManageSdk;
import com.kedacom.device.core.service.DecoderService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.MspRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: hxj
 * @Date: 2021/5/11 13:17
 */
@Service
@Slf4j
public class DecoderServiceImpl implements DecoderService {

    @Autowired
    private DecoderManageSdk decoderManageSdk;

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private MspRestTemplate mspRestTemplate;

    @Value("${zf.msp.server_addr}")
    private String mspUrl;

    private static final String mspPath = "/api/v1/manage/decoder/";

    @Override
    public OsdConfigResponse osdConfig(OsdConfigRequest request) {
        log.info("配置解码通道字幕信息入参信息:{}", JSON.toJSONString(request));

        String response = mspRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "osd/config", JSON.toJSONString(request), String.class);
        OsdConfigResponse configResponse = JSONObject.parseObject(response, OsdConfigResponse.class);

        // OsdConfigResponse response = decoderManageSdk.osdConfig(request);

        log.info("配置解码通道字幕信息应答信息:{}", configResponse);
        if (configResponse != null) {
            responseUtil.handleMSPRes("配置解码通道字幕信息异常:{},{},{}", DeviceErrorEnum.DECODER_OSD_CONFIG_FAILED, configResponse.getError(), configResponse.getErrstr());
        }
        return configResponse;
    }

    @Override
    public OsdDeleteResponse osdDelete(OsdDeleteRequest request) {
        log.info("取消解码通道的字幕显示入参信息:{}", JSON.toJSONString(request));

        String response = mspRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "osd/delete", JSON.toJSONString(request), String.class);
        OsdDeleteResponse deleteResponse = JSONObject.parseObject(response, OsdDeleteResponse.class);

        // OsdDeleteResponse response = decoderManageSdk.osdDelete(request);

        log.info("取消解码通道的字幕显示应答信息:{}", deleteResponse);
        if (deleteResponse != null) {
            responseUtil.handleMSPRes("取消解码通道的字幕显示异常:{},{},{}", DeviceErrorEnum.DECODER_OSD_DELETE_FAILED, deleteResponse.getError(), deleteResponse.getErrstr());
        }
        return deleteResponse;
    }

    @Override
    public StyleQueryResponse styleQuery(StyleQueryRequest request) {
        log.info("查询解码通道的画面风格及最大解码能力入参信息:{}", JSON.toJSONString(request));

        String response = mspRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "style/query", JSON.toJSONString(request), String.class);
        StyleQueryResponse queryResponse = JSONObject.parseObject(response, StyleQueryResponse.class);

        // StyleQueryResponse response = decoderManageSdk.styleQuery(request);

        log.info("查询解码通道的画面风格及最大解码能力应答信息:{}", queryResponse);
        if (queryResponse != null) {
            responseUtil.handleMSPRes("查询解码通道的画面风格及最大解码能力异常:{},{},{}", DeviceErrorEnum.DECODER_STYLE_QUERY_FAILED, queryResponse.getError(), queryResponse.getErrstr());
        }
        return queryResponse;
    }

    @Override
    public void styleConfig(StyleConfigRequest request) {
        log.info("设置解码通道的画面风格入参信息:{}", JSON.toJSONString(request));

        String response = mspRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "style/config", JSON.toJSONString(request), String.class);
        StyleConfigResponse configResponse = JSONObject.parseObject(response, StyleConfigResponse.class);

        //  StyleConfigResponse response = decoderManageSdk.styleConfig(request);

        log.info("设置解码通道的画面风格应答信息:{}", configResponse);
        if (configResponse != null) {
            responseUtil.handleMSPRes("设置解码通道的画面风格异常:{},{},{}", DeviceErrorEnum.DECODER_STYLE_CONFIG_FAILED, configResponse.getError(), configResponse.getErrstr());
        }
    }

}
