package com.kedacom.device.core.service.impl;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/5/11 13:17
 */
@Service
@Slf4j
public class DecoderServiceImpl implements DecoderService {

    @Resource
    private DecoderManageSdk decoderManageSdk;

    @Autowired
    private HandleResponseUtil responseUtil;

    @Override
    public OsdConfigResponse osdConfig(OsdConfigRequest request) {
        log.info("配置解码通道字幕信息入参信息:{}", request);
        OsdConfigResponse response = decoderManageSdk.osdConfig(request);
        log.info("配置解码通道字幕信息应答信息:{}", response);
        responseUtil.handleMSPRes("配置解码通道字幕信息异常:{},{},{}", DeviceErrorEnum.DECODER_OSD_CONFIG_FAILED, response.getError(), response.getErrstr());
        return response;
    }

    @Override
    public OsdDeleteResponse osdDelete(OsdDeleteRequest request) {
        log.info("取消解码通道的字幕显示入参信息:{}", request);
        OsdDeleteResponse response = decoderManageSdk.osdDelete(request);
        log.info("取消解码通道的字幕显示应答信息:{}", response);
        responseUtil.handleMSPRes("取消解码通道的字幕显示异常:{},{},{}", DeviceErrorEnum.DECODER_OSD_DELETE_FAILED, response.getError(), response.getErrstr());
        return response;
    }

    @Override
    public StyleQueryResponse styleQuery(StyleQueryRequest request) {
        log.info("查询解码通道的画面风格及最大解码能力入参信息:{}", request);
        StyleQueryResponse response = decoderManageSdk.styleQuery(request);
        log.info("查询解码通道的画面风格及最大解码能力应答信息:{}", response);
        responseUtil.handleMSPRes("查询解码通道的画面风格及最大解码能力异常:{},{},{}", DeviceErrorEnum.DECODER_STYLE_QUERY_FAILED, response.getError(), response.getErrstr());
        return response;
    }

    @Override
    public void styleConfig(StyleConfigRequest request) {
        log.info("设置解码通道的画面风格入参信息:{}", request);
        StyleConfigResponse response = decoderManageSdk.styleConfig(request);
        log.info("设置解码通道的画面风格应答信息:{}", response);
        responseUtil.handleMSPRes("设置解码通道的画面风格异常:{},{},{}", DeviceErrorEnum.DECODER_STYLE_CONFIG_FAILED, response.getError(), response.getErrstr());
    }

}
