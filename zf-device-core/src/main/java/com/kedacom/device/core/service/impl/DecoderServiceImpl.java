package com.kedacom.device.core.service.impl;

import com.kedacom.avIntegration.request.decoder.OsdConfigRequest;
import com.kedacom.avIntegration.request.decoder.OsdDeleteRequest;
import com.kedacom.avIntegration.request.decoder.StyleConfigRequest;
import com.kedacom.avIntegration.request.decoder.StyleQueryRequest;
import com.kedacom.avIntegration.response.decoder.OsdConfigResponse;
import com.kedacom.avIntegration.response.decoder.OsdDeleteResponse;
import com.kedacom.avIntegration.response.decoder.StyleConfigResponse;
import com.kedacom.avIntegration.response.decoder.StyleQueryResponse;
import com.kedacom.device.core.data.DeviceConstants;
import com.kedacom.device.core.data.DeviceErrorEnum;
import com.kedacom.device.core.exception.DecoderException;
import com.kedacom.device.core.msp.DecoderManageSdk;
import com.kedacom.device.core.service.DecoderService;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public OsdConfigResponse osdConfig(OsdConfigRequest request) {
        log.info("配置解码通道字幕信息入参信息 —— OsdConfigRequest:{}", request);
        OsdConfigResponse response = decoderManageSdk.osdConfig(request);
        log.info("配置解码通道字幕信息应答信息 —— OsdConfigResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new DecoderException(DeviceErrorEnum.DECODER_OSD_CONFIG_FAILED.getCode(), DeviceErrorEnum.DECODER_OSD_CONFIG_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public OsdDeleteResponse osdDelete(OsdDeleteRequest request) {
        log.info("取消解码通道的字幕显示入参信息 —— OsdDeleteRequest:{}", request);
        OsdDeleteResponse response = decoderManageSdk.osdDelete(request);
        log.info("取消解码通道的字幕显示应答信息 —— OsdDeleteResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new DecoderException(DeviceErrorEnum.DECODER_OSD_DELETE_FAILED.getCode(), response.getErrstr());
        }
        return response;
    }

    @Override
    public StyleQueryResponse styleQuery(StyleQueryRequest request) {
        log.info("查询解码通道的画面风格及最大解码能力入参信息 —— StyleQueryRequest:{}", request);
        StyleQueryResponse response = decoderManageSdk.styleQuery(request);
        log.info("查询解码通道的画面风格及最大解码能力应答信息 —— StyleQueryResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new DecoderException(DeviceErrorEnum.DECODER_STYLE_QUERY_FAILED.getCode(), response.getErrstr());
        }
        return response;
    }

    @Override
    public StyleConfigResponse styleConfig(StyleConfigRequest request) {
        log.info("设置解码通道的画面风格入参信息 —— StyleConfigRequest:{}", request);
        StyleConfigResponse response = decoderManageSdk.styleConfig(request);
        log.info("设置解码通道的画面风格应答信息 —— StyleConfigResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new DecoderException(DeviceErrorEnum.DECODER_STYLE_CONFIG_FAILED.getCode(), response.getErrstr());
        }
        return response;
    }
}
