package com.kedacom.device.service.impl;

import com.kedacom.BaseResult;
import com.kedacom.acl.network.unite.StreamMediaInterface;
import com.kedacom.device.service.StreamMediaService;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.QueryAudioMixResponseVO;
import com.kedacom.streamMedia.response.QueryrecResponseVO;
import com.kedacom.streamMedia.response.StartAudioMixResponseVO;
import com.kedacom.streamMedia.response.StartrecResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:35
 */
@Slf4j
@Service
public class StreamMediaServiceImpl implements StreamMediaService {

    @Resource
    private StreamMediaInterface streamMediaInterface;

    @Override
    public BaseResult<StartrecResponseVO> startRec(StartRecRequestDTO startRecRequestDTO) {
        log.info("开启录像入参信息 —— startRecRequestDTO:{}", startRecRequestDTO);
        String ssid = "";
        BaseResult<StartrecResponseVO> startRec = streamMediaInterface.startRec(ssid, startRecRequestDTO);
        log.info("开启录像应答信息 —— startRec:{}",startRec);
        return startRec;
    }

    @Override
    public BaseResult<Boolean> stopRec(StopRecRequestDTO stopRecRequestDTO) {
        log.info("停止录像入参信息 —— stopRecRequestDTO:{}", stopRecRequestDTO);
        String ssid = "";
        BaseResult<Boolean> stopRec = streamMediaInterface.stopRec(ssid, stopRecRequestDTO);
        log.info("停止录像应答信息 —— stopRec:{}",stopRec);
        return stopRec;
    }

    @Override
    public BaseResult<QueryrecResponseVO> queryRec(QueryRecRequestDTO queryRecRequestDTO) {
        log.info("查询录像记录入参信息 —— queryRecRequestDTO:{}", queryRecRequestDTO);
        String ssid = "";
        BaseResult<QueryrecResponseVO> queryRec = streamMediaInterface.queryRec(ssid, queryRecRequestDTO);
        log.info("查询录像记录应答信息 —— queryRec:{}",queryRec);
        return queryRec;
    }

    @Override
    public BaseResult<StartAudioMixResponseVO> startAudioMix(StartAudioMixRequestDTO startAudioMixRequestDTO) {
        log.info("开启音频混音入参信息 —— startAudioMixRequestDTO:{}", startAudioMixRequestDTO);
        String ssid = "";
        BaseResult<StartAudioMixResponseVO> startAudioMix = streamMediaInterface.startAudioMix(ssid, startAudioMixRequestDTO);
        log.info("开启音频混音应答信息 —— startAudioMix:{}",startAudioMix);
        return startAudioMix;
    }

    @Override
    public BaseResult<Boolean> stopAudioMix(StopAudioMixRequestDTO stopAudioMixRequestDTO) {
        log.info("停止音频混音入参信息 —— stopAudioMixRequestDTO:{}", stopAudioMixRequestDTO);
        String ssid = "";
        BaseResult<Boolean> stopAudioMix = streamMediaInterface.stopAudioMix(ssid, stopAudioMixRequestDTO);
        log.info("停止音频混音应答信息 —— stopAudioMix:{}",stopAudioMix);
        return stopAudioMix;
    }

    @Override
    public BaseResult<Boolean> updateAudioMix(UpdateAudioMixRequestDTO updateAudioMixRequestDTO) {
        log.info("更新音频混音入参信息 —— updateAudioMixRequestDTO:{}", updateAudioMixRequestDTO);
        String ssid = "";
        BaseResult<Boolean> updateAudioMix = streamMediaInterface.updateAudioMix(ssid, updateAudioMixRequestDTO);
        log.info("更新音频混音应答信息 —— updateAudioMix:{}",updateAudioMix);
        return updateAudioMix;
    }

    @Override
    public BaseResult<List<String>> queryAllAudioMix(QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO) {
        log.info("更新音频混音入参信息 —— queryAllAudioMixRequestDTO:{}", queryAllAudioMixRequestDTO);
        String ssid = "";
        BaseResult<List<String>> queryAllAudioMix = streamMediaInterface.queryAllAudioMix(ssid, queryAllAudioMixRequestDTO);
        log.info("更新音频混音应答信息 —— queryAllAudioMix:{}",queryAllAudioMix);
        return queryAllAudioMix;
    }

    @Override
    public BaseResult<QueryAudioMixResponseVO> queryAudioMix(QueryAudioMixRequestDTO queryAudioMixRequestDTO) {
        log.info("更新音频混音入参信息 —— queryAudioMixRequestDTO:{}", queryAudioMixRequestDTO);
        String ssid = "";
        BaseResult<QueryAudioMixResponseVO> queryAudioMix = streamMediaInterface.queryAudioMix(ssid, queryAudioMixRequestDTO);
        log.info("更新音频混音应答信息 —— queryAudioMix:{}",queryAudioMix);
        return queryAudioMix;
    }


}
