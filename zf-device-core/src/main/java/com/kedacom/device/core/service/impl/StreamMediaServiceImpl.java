package com.kedacom.device.core.service.impl;

import com.kedacom.acl.network.unite.StreamMediaInterface;
import com.kedacom.device.core.service.StreamMediaService;
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

    @Override
    public StartRecResponseVO startRec(StartRecRequestDTO startRecRequestDTO) {
        log.info("开启录像入参信息 —— startRecRequestDTO:{}", startRecRequestDTO);
        String ssid = "";
        StartRecResponseVO startRec = streamMediaInterface.startRec(ssid, startRecRequestDTO);
        log.info("开启录像应答信息 —— startRec:{}",startRec);
        return startRec;
    }

    @Override
    public Boolean stopRec(StopRecRequestDTO stopRecRequestDTO) {
        log.info("停止录像入参信息 —— stopRecRequestDTO:{}", stopRecRequestDTO);
        String ssid = "";
        Boolean stopRec = streamMediaInterface.stopRec(ssid, stopRecRequestDTO);
        log.info("停止录像应答信息 —— stopRec:{}",stopRec);
        return stopRec;
    }

    @Override
    public QueryRecResponseVO queryRec(QueryRecRequestDTO queryRecRequestDTO) {
        log.info("查询录像记录入参信息 —— queryRecRequestDTO:{}", queryRecRequestDTO);
        String ssid = "";
        QueryRecResponseVO queryRec = streamMediaInterface.queryRec(ssid, queryRecRequestDTO);
        log.info("查询录像记录应答信息 —— queryRec:{}",queryRec);
        return queryRec;
    }

    @Override
    public String startAudioMix(StartAudioMixRequestDTO startAudioMixRequestDTO) {
        log.info("开启音频混音入参信息 —— startAudioMixRequestDTO:{}", startAudioMixRequestDTO);
        String ssid = "";
        String startAudioMix = streamMediaInterface.startAudioMix(ssid, startAudioMixRequestDTO);
        log.info("开启音频混音应答信息 —— startAudioMix:{}",startAudioMix);
        return startAudioMix;
    }

    @Override
    public Boolean stopAudioMix(StopAudioMixRequestDTO stopAudioMixRequestDTO) {
        log.info("停止音频混音入参信息 —— stopAudioMixRequestDTO:{}", stopAudioMixRequestDTO);
        String ssid = "";
        Boolean stopAudioMix = streamMediaInterface.stopAudioMix(ssid, stopAudioMixRequestDTO);
        log.info("停止音频混音应答信息 —— stopAudioMix:{}",stopAudioMix);
        return stopAudioMix;
    }

    @Override
    public Boolean updateAudioMix(UpdateAudioMixRequestDTO updateAudioMixRequestDTO) {
        log.info("更新音频混音入参信息 —— updateAudioMixRequestDTO:{}", updateAudioMixRequestDTO);
        String ssid = "";
        Boolean updateAudioMix = streamMediaInterface.updateAudioMix(ssid, updateAudioMixRequestDTO);
        log.info("更新音频混音应答信息 —— updateAudioMix:{}",updateAudioMix);
        return updateAudioMix;
    }

    @Override
    public List<String> queryAllAudioMix(QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO) {
        log.info("查询所有混音入参信息 —— queryAllAudioMixRequestDTO:{}", queryAllAudioMixRequestDTO);
        String ssid = "";
        List<String> queryAllAudioMix = streamMediaInterface.queryAllAudioMix(ssid, queryAllAudioMixRequestDTO);
        log.info("查询所有混音应答信息 —— queryAllAudioMix:{}",queryAllAudioMix);
        return queryAllAudioMix;
    }

    @Override
    public QueryAudioMixResponseVO queryAudioMix(QueryAudioMixRequestDTO queryAudioMixRequestDTO) {
        log.info("查询混音信息入参信息 —— queryAudioMixRequestDTO:{}", queryAudioMixRequestDTO);
        String ssid = "";
        QueryAudioMixResponseVO queryAudioMix = streamMediaInterface.queryAudioMix(ssid, queryAudioMixRequestDTO);
        log.info("查询混音信息应答信息 —— queryAudioMix:{}",queryAudioMix);
        return queryAudioMix;
    }

    @Override
    public String startVideoMix(StartVideoMixRequestDTO startVideoMixRequestDTO) {
        log.info("开始画面合成（不支持语音激励功能）入参信息 —— startVideoMixRequestDTO:{}", startVideoMixRequestDTO);
        String ssid = "";
        String startVideoMix = streamMediaInterface.startVideoMix(ssid, startVideoMixRequestDTO);
        log.info("开始画面合成（不支持语音激励功能）应答信息 —— startVideoMix:{}",startVideoMix);
        return startVideoMix;
    }

    @Override
    public Boolean stopVideoMix(StopVideoMixRequestDTO stopVideoMixRequestDTO) {
        log.info("停止画面合成（不支持语音激励功能）入参信息 —— stopVideoMixRequestDTO:{}", stopVideoMixRequestDTO);
        String ssid = "";
        Boolean stopVideoMix = streamMediaInterface.stopVideoMix(ssid, stopVideoMixRequestDTO);
        log.info("停止画面合成（不支持语音激励功能）应答信息 —— stopVideoMix:{}",stopVideoMix);
        return stopVideoMix;
    }

    @Override
    public Boolean updateVideoMix(UpdateVideoMixRequestDTO updateVideoMixRequestDTO) {
        log.info("更新画面合成（不支持语音激励功能）入参信息 —— updateVideoMixRequestDTO:{}", updateVideoMixRequestDTO);
        String ssid = "";
        Boolean updateVideoMix = streamMediaInterface.updateVideoMix(ssid, updateVideoMixRequestDTO);
        log.info("更新画面合成（不支持语音激励功能）应答信息 —— updateVideoMix:{}",updateVideoMix);
        return updateVideoMix;
    }

    @Override
    public List<String> queryAllVideoMix(String unitId) {
        log.info("查询所有画面合成（不支持语音激励功能）入参信息 —— unitId:{}", unitId);
        String ssid = "";
        List<String> queryAllVideoMix = streamMediaInterface.queryAllVideoMix(ssid);
        log.info("查询所有画面合成（不支持语音激励功能）应答信息 —— queryAllVideoMix:{}",queryAllVideoMix);
        return queryAllVideoMix;
    }

    @Override
    public QueryVideoMixResponseVO queryVideoMix(QueryVideoMixRequestDTO queryVideoMixRequestDTO) {
        log.info("查询画面信息（不支持语音激励功能）入参信息 —— queryVideoMixRequestDTO:{}", queryVideoMixRequestDTO);
        String ssid = "";
        QueryVideoMixResponseVO queryVideoMix = streamMediaInterface.queryVideoMix(ssid,queryVideoMixRequestDTO);
        log.info("查询画面信息（不支持语音激励功能）应答信息 —— queryVideoMix:{}",queryVideoMix);
        return queryVideoMix;
    }
}
