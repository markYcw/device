package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
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
    public StartRecResponseVO startRec(StartRecRequest request) {
        log.info("开启录像入参信息:{}", request);

        Integer ssid = 1;
        StartRecDTO startRecDTO = streamMediaConvert.convertStartRecRequest(request);
        startRecDTO.setAccount_token("123");
        startRecDTO.setRequest_id("321321");
        startRecDTO.buildData(ssid);
        log.info("开启录像交互参数:{}", startRecDTO);
        StartRecResponse res = client.startRec(startRecDTO);
        log.info("开启录像应答信息:{}", res);
        String errorMsg = "开启录像失败:{},{}";
        handleRes(errorMsg, res);
        return res.acquireData(StartRecResponseVO.class);
    }

    @Override
    public Boolean stopRec(StopRecRequest request) {
        log.info("停止录像入参信息:{}", request);

        Integer ssid = 1;
        StopRecDTO stopRecDTO = streamMediaConvert.convertStopRecRequest(request);
        stopRecDTO.setAccount_token("123");
        stopRecDTO.setRequest_id("321321");
        stopRecDTO.buildData(ssid);
        log.info("停止录像交互参数:{}", stopRecDTO);
        StreamMediaResponse response = client.stopRec(stopRecDTO);
        log.info("停止录像应答信息:{}", response);
        String error = "停止录像失败:{},{}";
        handleRes(error, response);
        return true;
    }

    @Override
    public QueryRecResponseVO queryRec(QueryRecRequest request) {
        String error = "查询录像记录失败:{},{}";
        log.info("查询录像记录入参信息:{}", request);

        Integer ssid = 1;
        QueryRecDTO queryRecDTO = streamMediaConvert.convertQueryRecRequest(request);
        queryRecDTO.buildData(ssid);
        log.info("查询录像记录交互参数:{}", queryRecDTO);
        QueryRecResponse res = client.queryRec(queryRecDTO);
        log.info("查询录像记录应答信息:{}", res);
        handleRes(error, res);
        return res.acquireData(QueryRecResponseVO.class);
    }

    @Override
    public StartAudioMixResponseVO startAudioMix(StartAudioMixRequest request) {
        String error = "开启音频混音失败:{},{}";
        log.info("开启音频混音入参信息:{}", request);

        Integer ssid = 1;
        // 自定义的分组id
        String groupId = UUID.randomUUID().toString().replace("-", "");
        StartAudioMixDTO startAudioMixDTO = streamMediaConvert.convertStartAudioMixRequest(request);
        startAudioMixDTO.setGroupID(groupId);
        startAudioMixDTO.buildData(ssid);
        log.info("开启音频混音交互参数:{}", startAudioMixDTO);
        StartAudioMixResponse res = client.startAudioMix(startAudioMixDTO);
        handleRes(error, res);
        StartAudioMixResponseVO response = new StartAudioMixResponseVO();
        response.setMixID(res.getMixID());
        response.setGroupID(groupId);
        log.info("开启音频混音返参:{}", response);
        return response;
    }

    @Override
    public Boolean stopAudioMix(StopAudioMixRequest request) {
        String error = "停止音频混音失败:{},{}";
        log.info("停止音频混音入参信息:{}", request);

        Integer ssid = 1;
        StopAudioMixDTO stopAudioMixDTO = streamMediaConvert.convertStopAudioMixRequest(request);
        stopAudioMixDTO.buildData(ssid);
        log.info("停止音频混音交互参数:{}", stopAudioMixDTO);
        StreamMediaResponse res = client.stopAudioMix(stopAudioMixDTO);
        log.info("停止音频混音应答信息:{}", res);
        handleRes(error, res);
        return true;
    }

    @Override
    public Boolean updateAudioMix(UpdateAudioMixRequest request) {
        String error = "更新音频混音失败:{},{}";
        log.info("更新音频混音入参信息:{}", request);

        Integer ssid = 1;
        UpdateAudioMixDTO updateAudioMixDTO = streamMediaConvert.convertUpdateAudioMixRequest(request);
        updateAudioMixDTO.buildData(ssid);
        log.info("更新音频混音交互参数:{}", updateAudioMixDTO);
        StreamMediaResponse res = client.updateAudioMix(updateAudioMixDTO);
        log.info("更新音频混音应答信息:{}", res);
        handleRes(error, res);
        return true;
    }

    @Override
    public List<String> queryAllAudioMix(QueryAllAudioMixRequest request) {
        String error = "查询所有混音失败:{},{}";
        log.info("查询所有混音入参信息:{}", request);
        Integer ssid = 1;

        QueryAllAudioMixDTO queryAllAudioMixDTO = streamMediaConvert.convertQueryAllAudioMixRequest(request);
        queryAllAudioMixDTO.buildData(ssid);
        log.info("查询所有混音交互参数:{}", queryAllAudioMixDTO);
        QueryAllAudioMixResponse res = client.queryAllAudioMix(queryAllAudioMixDTO);
        log.info("查询所有混音应答信息:{}", res);
        handleRes(error, res);
        return res.acquireData(List.class);
    }

    @Override
    public QueryAudioMixResponseVO queryAudioMix(QueryAudioMixRequest request) {
        String error = "查询混音信息失败:{},{}";
        log.info("查询混音信息入参信息:{}", request);

        Integer ssid = 1;
        QueryAudioMixDTO queryAudioMixDTO = streamMediaConvert.convertQueryAudioMixRequest(request);
        queryAudioMixDTO.buildData(ssid);
        log.info("查询混音信息交互参数:{}", queryAudioMixDTO);
        QueryAudioMixResponse res = client.queryAudioMix(queryAudioMixDTO);
        handleRes(error, res);
        log.info("查询混音信息应答信息:{}", res);
        return res.acquireData(QueryAudioMixResponseVO.class);
    }

    @Override
    public StartVideoMixResponseVO startVideoMix(StartVideoMixRequest request) {
        String error = "开始画面合成失败:{},{}";
        log.info("开始画面合成入参信息:{}", request);

        Integer ssid = 1;
        String groupId = UUID.randomUUID().toString().replace("-", "");
        StartVideoMixDTO startVideoMixDTO = streamMediaConvert.convertStartVideoMixRequest(request);
        startVideoMixDTO.setGroupID(groupId);
        startVideoMixDTO.buildData(ssid);
        log.info("开始画面合成交互参数:{}", startVideoMixDTO);
        StartVideoMixResponse res = client.startVideoMix(startVideoMixDTO);
        handleRes(error, res);
        StartVideoMixResponseVO response = new StartVideoMixResponseVO();
        response.setMixID(res.getMixID());
        response.setGroupID(groupId);
        log.info("开始画面合成应答:{}", response);
        handleRes(error, res);
        return response;
    }

    @Override
    public Boolean stopVideoMix(StopVideoMixRequest request) {
        String error = "停止画面合成失败:{},{}";
        log.info("停止画面合成入参信息:{}", request);

        Integer ssid = 1;
        StopVideoMixDTO stopVideoMixDTO = streamMediaConvert.convertStopVideoMixRequest(request);
        stopVideoMixDTO.buildData(ssid);
        log.info("停止画面合成交互参数:{}", stopVideoMixDTO);
        StreamMediaResponse res = client.stopVideoMix(stopVideoMixDTO);
        handleRes(error, res);
        log.info("停止画面合成应答信息:{}", res);
        return true;
    }

    @Override
    public Boolean updateVideoMix(UpdateVideoMixRequest request) {
        String error = "更新画面合成失败:{},{}";
        log.info("更新画面合成入参信息:{}", request);

        Integer ssid = 1;
        UpdateVideoMixDTO updateVideoMixDTO = streamMediaConvert.convertUpdateVideoMixRequest(request);
        updateVideoMixDTO.buildData(ssid);
        log.info("更新画面合成交互参数:{}", updateVideoMixDTO);
        StreamMediaResponse res = client.updateVideoMix(updateVideoMixDTO);
        handleRes(error, res);
        log.info("更新画面合成应答信息:{}", res);
        return true;
    }

    @Override
    public List<String> queryAllVideoMix(String unitId) {
        String error = "查询所有画面合成失败:{},{}";
        log.info("查询所有画面合成入参信息 :{}", unitId);

        Integer ssid = 1;
        QueryAllVideoMixDTO queryAllVideoMixDTO = new QueryAllVideoMixDTO();
        queryAllVideoMixDTO.buildData(ssid);
        QueryAllAudioMixResponse res = client.queryAllVideoMix(queryAllVideoMixDTO);
        handleRes(error, res);
        log.info("查询所有画面合成应答信息:{}", res);
        return res.acquireData(List.class);
    }

    @Override
    public QueryVideoMixResponseVO queryVideoMix(QueryVideoMixRequest request) {
        String error = "查询画面信息失败:{},{}";
        log.info("查询画面信息入参信息:{}", request);

        Integer ssid = 1;
        QueryVideoMixDTO queryVideoMixDTO = streamMediaConvert.convertQueryVideoMixRequest(request);
        queryVideoMixDTO.buildData(ssid);
        log.info("查询画面信息交互参数:{}", queryVideoMixDTO);
        QueryVideoMixResponse res = client.queryVideoMix(queryVideoMixDTO);
        handleRes(error, res);
        log.info("查询画面信息应答信息:{}", res);
        return res.acquireData(QueryVideoMixResponseVO.class);
    }

    private void handleRes(String str, StreamMediaResponse res) {
        if (res.getErrcode() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(kmErrCode.matchErrMsg(res.getErrcode()))) {
                log.error(str, DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), kmErrCode.matchErrMsg(res.getErrcode()));
                throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), kmErrCode.matchErrMsg(res.getErrcode()));
            } else {
                log.error(str, DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), DeviceErrorEnum.STREAM_MEDIA_FAILED.getMsg());
                throw new StreamMediaException(DeviceErrorEnum.STREAM_MEDIA_FAILED.getCode(), DeviceErrorEnum.STREAM_MEDIA_FAILED.getMsg());
            }
        }
    }
}
