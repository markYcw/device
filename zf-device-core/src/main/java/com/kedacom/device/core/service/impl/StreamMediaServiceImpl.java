package com.kedacom.device.core.service.impl;

import com.kedacom.acl.network.unite.StreamMediaInterface;
import com.kedacom.device.core.convert.StreamMediaConvert;
import com.kedacom.device.core.service.StreamMediaService;
import com.kedacom.streamMedia.dto.*;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.QueryAudioMixResponseVO;
import com.kedacom.streamMedia.response.QueryRecResponseVO;
import com.kedacom.streamMedia.response.QueryVideoMixResponseVO;
import com.kedacom.streamMedia.response.StartRecResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:35
 */
@Slf4j
@Service
public class StreamMediaServiceImpl implements StreamMediaService {

    @Autowired
    private StreamMediaInterface streamMediaInterface;

    @Autowired
    private StreamMediaConvert streamMediaConvert;

    @Override
    public StartRecResponseVO startRec(StartRecRequest request) {
        log.info("开启录像入参信息:{}", request);
        String ssid = "1";
        StartRecRequestDTO startRecRequestDTO = streamMediaConvert.convertStartRecRequest(request);
        startRecRequestDTO.setAccount_token("123");
        startRecRequestDTO.setRequest_id("321321");
        StartRecResponseVO response = streamMediaInterface.startRec(ssid, startRecRequestDTO);
        log.info("开启录像应答信息:{}",response);
        return response;
    }

    @Override
    public Boolean stopRec(StopRecRequest request) {
        log.info("停止录像入参信息:{}", request);
        String ssid = "";
        StopRecRequestDTO stopRecRequestDTO = streamMediaConvert.convertStopRecRequest(request);
        stopRecRequestDTO.setAccount_token("123");
        stopRecRequestDTO.setRequest_id("321321");
        Boolean response = streamMediaInterface.stopRec(ssid, stopRecRequestDTO);
        log.info("停止录像应答信息:{}",response);
        return response;
    }

    @Override
    public QueryRecResponseVO queryRec(QueryRecRequest request) {
        log.info("查询录像记录入参信息:{}", request);
        String ssid = "";
        QueryRecRequestDTO queryRecRequestDTO = streamMediaConvert.convertQueryRecRequest(request);
        QueryRecResponseVO queryRec = streamMediaInterface.queryRec(ssid, queryRecRequestDTO);
        log.info("查询录像记录应答信息:{}",queryRec);
        return queryRec;
    }

    @Override
    public String startAudioMix(StartAudioMixRequest request) {
        log.info("开启音频混音入参信息:{}", request);
        String ssid = "";
        StartAudioMixRequestDTO startAudioMixRequestDTO = streamMediaConvert.convertStartAudioMixRequest(request);
        String startAudioMix = streamMediaInterface.startAudioMix(ssid, startAudioMixRequestDTO);
        log.info("开启音频混音应答信息:{}",startAudioMix);
        return startAudioMix;
    }

    @Override
    public Boolean stopAudioMix(StopAudioMixRequest request) {
        log.info("停止音频混音入参信息:{}", request);
        String ssid = "";
        StopAudioMixRequestDTO stopAudioMixRequestDTO = streamMediaConvert.convertStopAudioMixRequest(request);
        Boolean stopAudioMix = streamMediaInterface.stopAudioMix(ssid, stopAudioMixRequestDTO);
        log.info("停止音频混音应答信息:{}",stopAudioMix);
        return stopAudioMix;
    }

    @Override
    public Boolean updateAudioMix(UpdateAudioMixRequest request) {
        log.info("更新音频混音入参信息:{}", request);
        String ssid = "";
        UpdateAudioMixRequestDTO updateAudioMixRequestDTO = streamMediaConvert.convertUpdateAudioMixRequest(request);
        Boolean updateAudioMix = streamMediaInterface.updateAudioMix(ssid, updateAudioMixRequestDTO);
        log.info("更新音频混音应答信息:{}",updateAudioMix);
        return updateAudioMix;
    }

    @Override
    public List<String> queryAllAudioMix(QueryAllAudioMixRequest request) {
        log.info("查询所有混音入参信息:{}", request);
        String ssid = "";
        QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO = streamMediaConvert.convertQueryAllAudioMixRequest(request);
        List<String> queryAllAudioMix = streamMediaInterface.queryAllAudioMix(ssid, queryAllAudioMixRequestDTO);
        log.info("查询所有混音应答信息 —— queryAllAudioMix:{}",queryAllAudioMix);
        return queryAllAudioMix;
    }

    @Override
    public QueryAudioMixResponseVO queryAudioMix(QueryAudioMixRequest request) {
        log.info("查询混音信息入参信息:{}", request);
        String ssid = "";
        QueryAudioMixRequestDTO queryAudioMixRequestDTO = streamMediaConvert.convertQueryAudioMixRequest(request);
        QueryAudioMixResponseVO queryAudioMix = streamMediaInterface.queryAudioMix(ssid, queryAudioMixRequestDTO);
        log.info("查询混音信息应答信息:{}",queryAudioMix);
        return queryAudioMix;
    }

    @Override
    public String startVideoMix(StartVideoMixRequest request) {
        log.info("开始画面合成（不支持语音激励功能）入参信息:{}", request);
        String ssid = "";
        StartVideoMixRequestDTO startVideoMixRequestDTO = streamMediaConvert.convertStartVideoMixRequest(request);
        String startVideoMix = streamMediaInterface.startVideoMix(ssid, startVideoMixRequestDTO);
        log.info("开始画面合成（不支持语音激励功能）应答信息:{}",startVideoMix);
        return startVideoMix;
    }

    @Override
    public Boolean stopVideoMix(StopVideoMixRequest request) {
        log.info("停止画面合成（不支持语音激励功能）入参信息:{}", request);
        String ssid = "";
        StopVideoMixRequestDTO stopVideoMixRequestDTO = streamMediaConvert.convertStopVideoMixRequest(request);
        Boolean stopVideoMix = streamMediaInterface.stopVideoMix(ssid, stopVideoMixRequestDTO);
        log.info("停止画面合成（不支持语音激励功能）应答信息 —— stopVideoMix:{}",stopVideoMix);
        return stopVideoMix;
    }

    @Override
    public Boolean updateVideoMix(UpdateVideoMixRequest request) {
        log.info("更新画面合成（不支持语音激励功能）入参信息:{}", request);
        String ssid = "";
        UpdateVideoMixRequestDTO updateVideoMixRequestDTO = streamMediaConvert.convertUpdateVideoMixRequest(request);
        Boolean updateVideoMix = streamMediaInterface.updateVideoMix(ssid, updateVideoMixRequestDTO);
        log.info("更新画面合成（不支持语音激励功能）应答信息:{}",updateVideoMix);
        return updateVideoMix;
    }

    @Override
    public List<String> queryAllVideoMix(String unitId) {
        log.info("查询所有画面合成（不支持语音激励功能）入参信息 :{}", unitId);
        String ssid = "";
        List<String> queryAllVideoMix = streamMediaInterface.queryAllVideoMix(ssid);
        log.info("查询所有画面合成（不支持语音激励功能）应答信息:{}",queryAllVideoMix);
        return queryAllVideoMix;
    }

    @Override
    public QueryVideoMixResponseVO queryVideoMix(QueryVideoMixRequest request) {
        log.info("查询画面信息（不支持语音激励功能）入参信息:{}", request);
        String ssid = "";
        QueryVideoMixRequestDTO queryVideoMixRequestDTO = streamMediaConvert.convertQueryVideoMixRequest(request);
        QueryVideoMixResponseVO queryVideoMix = streamMediaInterface.queryVideoMix(ssid, queryVideoMixRequestDTO);
        log.info("查询画面信息（不支持语音激励功能）应答信息:{}",queryVideoMix);
        return queryVideoMix;
    }
}
