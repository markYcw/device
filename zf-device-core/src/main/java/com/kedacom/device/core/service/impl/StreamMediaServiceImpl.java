package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.device.core.config.KmErrCode;
import com.kedacom.device.core.convert.StreamMediaConvert;
import com.kedacom.device.core.data.DeviceConstants;
import com.kedacom.device.core.data.DeviceErrorEnum;
import com.kedacom.device.core.exception.StreamMediaException;
import com.kedacom.device.core.service.StreamMediaService;
import com.kedacom.device.stream.StreamMediaClient;
import com.kedacom.device.stream.request.*;
import com.kedacom.device.stream.response.*;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:35
 */
@Slf4j
@Service
public class StreamMediaServiceImpl implements StreamMediaService {

    @Autowired
    private StreamMediaConvert streamMediaConvert;
    @Autowired
    private StreamMediaClient client;
    @Autowired
    private KmErrCode kmErrCode;

    @Override
    public StartRecResponseVO startRec(StartRecDTO request) {
        log.info("开启录像入参信息:{}", request);

        Integer ssid = 1;
        StartRecRequest startRecRequest = streamMediaConvert.convertStartRecRequest(request);
        startRecRequest.setAccount_token("123");
        startRecRequest.setRequest_id("321321");
        startRecRequest.setSsid(ssid);
        log.info("开启录像交互参数:{}", startRecRequest);
        StartRecResponse res = client.startRec(startRecRequest);
        log.info("开启录像应答信息:{}", res);
        String errorMsg = "开启录像失败:{},{}";
        handleRes(errorMsg, res);
        return res.acquireData(StartRecResponseVO.class);
    }

    @Override
    public Boolean stopRec(StopRecDTO request) {
        log.info("停止录像入参信息:{}", request);

        Integer ssid = 1;
        StopRecRequest stopRecRequest = streamMediaConvert.convertStopRecRequest(request);
        stopRecRequest.setAccount_token("123");
        stopRecRequest.setRequest_id("321321");
        stopRecRequest.setSsid(ssid);
        log.info("停止录像交互参数:{}", stopRecRequest);
        BaseResponse response = client.stopRec(stopRecRequest);
        log.info("停止录像应答信息:{}", response);
        String error = "停止录像失败:{},{}";
        handleRes(error, response);
        return true;
    }

    @Override
    public QueryRecResponseVO queryRec(QueryRecDTO request) {
        String error = "查询录像记录失败:{},{}";
        log.info("查询录像记录入参信息:{}", request);

        Integer ssid = 1;
        QueryRecRequest queryRecRequest = streamMediaConvert.convertQueryRecRequest(request);
        queryRecRequest.setSsid(ssid);
        log.info("查询录像记录交互参数:{}", queryRecRequest);
        QueryRecResponse res = client.queryRec(queryRecRequest);
        log.info("查询录像记录应答信息:{}", res);
        handleRes(error, res);
        return res.acquireData(QueryRecResponseVO.class);
    }

    @Override
    public StartAudioMixResponseVO startAudioMix(StartAudioMixDTO request) {
        String error = "开启音频混音失败:{},{}";
        log.info("开启音频混音入参信息:{}", request);

        Integer ssid = 1;
        // 自定义的分组id
        String groupId = UUID.randomUUID().toString().replace("-", "");
        StartAudioMixRequest startAudioMixRequest = streamMediaConvert.convertStartAudioMixRequest(request);
        startAudioMixRequest.setGroupID(groupId);
        startAudioMixRequest.setSsid(ssid);
        log.info("开启音频混音交互参数:{}", startAudioMixRequest);
        StartAudioMixResponse res = client.startAudioMix(startAudioMixRequest);
        handleRes(error, res);
        StartAudioMixResponseVO response = new StartAudioMixResponseVO();
        response.setMixID(res.getMixID());
        response.setGroupID(groupId);
        log.info("开启音频混音返参:{}", response);
        return response;
    }

    @Override
    public Boolean stopAudioMix(StopAudioMixDTO request) {
        String error = "停止音频混音失败:{},{}";
        log.info("停止音频混音入参信息:{}", request);

        Integer ssid = 1;
        StopAudioMixRequest stopAudioMixRequest = streamMediaConvert.convertStopAudioMixRequest(request);
        stopAudioMixRequest.setSsid(ssid);
        log.info("停止音频混音交互参数:{}", stopAudioMixRequest);
        BaseResponse res = client.stopAudioMix(stopAudioMixRequest);
        log.info("停止音频混音应答信息:{}", res);
        handleRes(error, res);
        return true;
    }

    @Override
    public Boolean updateAudioMix(UpdateAudioMixDTO request) {
        String error = "更新音频混音失败:{},{}";
        log.info("更新音频混音入参信息:{}", request);

        Integer ssid = 1;
        UpdateAudioMixRequest updateAudioMixRequest = streamMediaConvert.convertUpdateAudioMixRequest(request);
        updateAudioMixRequest.setSsid(ssid);
        log.info("更新音频混音交互参数:{}", updateAudioMixRequest);
        BaseResponse res = client.updateAudioMix(updateAudioMixRequest);
        log.info("更新音频混音应答信息:{}", res);
        handleRes(error, res);
        return true;
    }

    @Override
    public List<String> queryAllAudioMix(QueryAllAudioMixDTO request) {
        String error = "查询所有混音失败:{},{}";
        log.info("查询所有混音入参信息:{}", request);
        Integer ssid = 1;

        QueryAllAudioMixRequest queryAllAudioMixRequest = streamMediaConvert.convertQueryAllAudioMixRequest(request);
        queryAllAudioMixRequest.setSsid(ssid);
        log.info("查询所有混音交互参数:{}", queryAllAudioMixRequest);
        QueryAllAudioMixResponse res = client.queryAllAudioMix(queryAllAudioMixRequest);
        log.info("查询所有混音应答信息:{}", res);
        handleRes(error, res);
        return res.acquireData(List.class);
    }

    @Override
    public QueryAudioMixResponseVO queryAudioMix(QueryAudioMixDTO request) {
        String error = "查询混音信息失败:{},{}";
        log.info("查询混音信息入参信息:{}", request);

        Integer ssid = 1;
        QueryAudioMixRequest queryAudioMixRequest = streamMediaConvert.convertQueryAudioMixRequest(request);
        queryAudioMixRequest.setSsid(ssid);
        log.info("查询混音信息交互参数:{}", queryAudioMixRequest);
        QueryAudioMixResponse res = client.queryAudioMix(queryAudioMixRequest);
        handleRes(error, res);
        log.info("查询混音信息应答信息:{}", res);
        return res.acquireData(QueryAudioMixResponseVO.class);
    }

    @Override
    public StartVideoMixResponseVO startVideoMix(StartVideoMixDTO request) {
        String error = "开始画面合成失败:{},{}";
        log.info("开始画面合成入参信息:{}", request);

        Integer ssid = 1;
        String groupId = UUID.randomUUID().toString().replace("-", "");
        StartVideoMixRequest startVideoMixRequest = streamMediaConvert.convertStartVideoMixRequest(request);
        startVideoMixRequest.setGroupID(groupId);
        startVideoMixRequest.setSsid(ssid);
        log.info("开始画面合成交互参数:{}", startVideoMixRequest);
        StartVideoMixResponse res = client.startVideoMix(startVideoMixRequest);
        handleRes(error, res);
        StartVideoMixResponseVO response = new StartVideoMixResponseVO();
        response.setMixID(res.getMixID());
        response.setGroupID(groupId);
        log.info("开始画面合成应答:{}", response);
        handleRes(error, res);
        return response;
    }

    @Override
    public Boolean stopVideoMix(StopVideoMixDTO request) {
        String error = "停止画面合成失败:{},{}";
        log.info("停止画面合成入参信息:{}", request);

        Integer ssid = 1;
        StopVideoMixRequest stopVideoMixRequest = streamMediaConvert.convertStopVideoMixRequest(request);
        stopVideoMixRequest.setSsid(ssid);
        log.info("停止画面合成交互参数:{}", stopVideoMixRequest);
        BaseResponse res = client.stopVideoMix(stopVideoMixRequest);
        handleRes(error, res);
        log.info("停止画面合成应答信息:{}", res);
        return true;
    }

    @Override
    public Boolean updateVideoMix(UpdateVideoMixDTO request) {
        String error = "更新画面合成失败:{},{}";
        log.info("更新画面合成入参信息:{}", request);

        Integer ssid = 1;
        UpdateVideoMixRequest updateVideoMixRequest = streamMediaConvert.convertUpdateVideoMixRequest(request);
        updateVideoMixRequest.setSsid(ssid);
        log.info("更新画面合成交互参数:{}", updateVideoMixRequest);
        BaseResponse res = client.updateVideoMix(updateVideoMixRequest);
        handleRes(error, res);
        log.info("更新画面合成应答信息:{}", res);
        return true;
    }

    @Override
    public List<String> queryAllVideoMix(String unitId) {
        String error = "查询所有画面合成失败:{},{}";
        log.info("查询所有画面合成入参信息 :{}", unitId);

        Integer ssid = 1;
        QueryAllVideoMixRequest queryAllVideoMixRequest = new QueryAllVideoMixRequest();
        queryAllVideoMixRequest.setSsid(ssid);
        QueryAllAudioMixResponse res = client.queryAllVideoMix(queryAllVideoMixRequest);
        handleRes(error, res);
        log.info("查询所有画面合成应答信息:{}", res);
        return res.acquireData(List.class);
    }

    @Override
    public QueryVideoMixResponseVO queryVideoMix(QueryVideoMixDTO request) {
        String error = "查询画面信息失败:{},{}";
        log.info("查询画面信息入参信息:{}", request);

        Integer ssid = 1;
        QueryVideoMixRequest queryVideoMixRequest = streamMediaConvert.convertQueryVideoMixRequest(request);
        queryVideoMixRequest.setSsid(ssid);
        log.info("查询画面信息交互参数:{}", queryVideoMixRequest);
        QueryVideoMixResponse res = client.queryVideoMix(queryVideoMixRequest);
        handleRes(error, res);
        log.info("查询画面信息应答信息:{}", res);
        return res.acquireData(QueryVideoMixResponseVO.class);
    }

    private void handleRes(String str, BaseResponse res) {
        if (res.acquireErrcode() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(kmErrCode.matchErrMsg(res.acquireErrcode()))) {
                log.error(str, DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), kmErrCode.matchErrMsg(res.acquireErrcode()));
                throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), kmErrCode.matchErrMsg(res.acquireSsno()));
            } else {
                log.error(str, DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), DeviceErrorEnum.STREAM_MEDIA_FAILED.getMsg());
                throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), DeviceErrorEnum.STREAM_MEDIA_FAILED.getMsg());
            }
        }
    }
}
