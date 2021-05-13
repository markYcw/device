package com.kedacom.device.core.service.impl;

import com.kedacom.acl.network.data.streamMeida.*;
import com.kedacom.acl.network.unite.StreamMediaInterface;
import com.kedacom.device.core.convert.StreamMediaConvert;
import com.kedacom.device.core.service.StreamMediaService;
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
    private StreamMediaInterface streamMediaInterface;

    @Autowired
    private StreamMediaConvert streamMediaConvert;

    @Override
    public StartRecResponseVO startRec(StartRecRequest request) {
        log.info("开启录像入参信息:{}", request);
        Integer ssid = 1 ;
        StartRecRequestDTO startRecRequestDTO = streamMediaConvert.convertStartRecRequest(request);
        startRecRequestDTO.setAccount_token("123");
        startRecRequestDTO.setRequest_id("321321");
        log.info("开启录像交互参数:{}",startRecRequestDTO);
        StartRecResponseVO response = streamMediaInterface.startRec(ssid, startRecRequestDTO);
        log.info("开启录像应答信息:{}",response);
        return response;
    }

    @Override
    public Boolean stopRec(StopRecRequest request) {
        log.info("停止录像入参信息:{}", request);
        Integer ssid = 1 ;

        StopRecRequestDTO stopRecRequestDTO = streamMediaConvert.convertStopRecRequest(request);
        stopRecRequestDTO.setAccount_token("123");
        stopRecRequestDTO.setRequest_id("321321");
        log.info("停止录像交互参数:{}",stopRecRequestDTO);
        Boolean response = streamMediaInterface.stopRec(ssid, stopRecRequestDTO);
        log.info("停止录像应答信息:{}",response);
        return response;
    }

    @Override
    public QueryRecResponseVO queryRec(QueryRecRequest request) {
        log.info("查询录像记录入参信息:{}", request);
        Integer ssid = 1 ;

        QueryRecRequestDTO queryRecRequestDTO = streamMediaConvert.convertQueryRecRequest(request);
        log.info("查询录像记录交互参数:{}",queryRecRequestDTO);
        QueryRecResponseVO queryRec = streamMediaInterface.queryRec(ssid, queryRecRequestDTO);
        log.info("查询录像记录应答信息:{}",queryRec);
        return queryRec;
    }

    @Override
    public StartAudioMixResponseVO startAudioMix(StartAudioMixRequest request) {
        log.info("开启音频混音入参信息:{}", request);
        Integer ssid = 1 ;

        // 自定义的分组id
        String groupId = UUID.randomUUID().toString().replace("-","");
        StartAudioMixRequestDTO startAudioMixRequestDTO = streamMediaConvert.convertStartAudioMixRequest(request);
        startAudioMixRequestDTO.setGroupID(groupId);
        log.info("开启音频混音交互参数:{}",startAudioMixRequestDTO);
        String startAudioMix = streamMediaInterface.startAudioMix(ssid, startAudioMixRequestDTO);
        StartAudioMixResponseVO response = new StartAudioMixResponseVO();
        response.setMixID(startAudioMix);
        response.setGroupID(groupId);
        log.info("开启音频混音应答信息:{}",response);
        return response;
    }

    @Override
    public Boolean stopAudioMix(StopAudioMixRequest request) {
        log.info("停止音频混音入参信息:{}", request);
        Integer ssid = 1 ;

        StopAudioMixRequestDTO stopAudioMixRequestDTO = streamMediaConvert.convertStopAudioMixRequest(request);
        log.info("停止音频混音交互参数:{}",stopAudioMixRequestDTO);
        Boolean stopAudioMix = streamMediaInterface.stopAudioMix(ssid, stopAudioMixRequestDTO);
        log.info("停止音频混音应答信息:{}",stopAudioMix);
        return stopAudioMix;
    }

    @Override
    public Boolean updateAudioMix(UpdateAudioMixRequest request) {
        log.info("更新音频混音入参信息:{}", request);
        Integer ssid = 1 ;

        UpdateAudioMixRequestDTO updateAudioMixRequestDTO = streamMediaConvert.convertUpdateAudioMixRequest(request);
        log.info("更新音频混音交互参数:{}",updateAudioMixRequestDTO);
        Boolean updateAudioMix = streamMediaInterface.updateAudioMix(ssid, updateAudioMixRequestDTO);
        log.info("更新音频混音应答信息:{}",updateAudioMix);
        return updateAudioMix;
    }

    @Override
    public List<String> queryAllAudioMix(QueryAllAudioMixRequest request) {
        log.info("查询所有混音入参信息:{}", request);
        Integer ssid = 1 ;

        QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO = streamMediaConvert.convertQueryAllAudioMixRequest(request);
        log.info("查询所有混音交互参数:{}",queryAllAudioMixRequestDTO);
        List<String> queryAllAudioMix = streamMediaInterface.queryAllAudioMix(ssid, queryAllAudioMixRequestDTO);
        log.info("查询所有混音应答信息:{}",queryAllAudioMix);
        return queryAllAudioMix;
    }

    @Override
    public QueryAudioMixResponseVO queryAudioMix(QueryAudioMixRequest request) {
        log.info("查询混音信息入参信息:{}", request);
        Integer ssid = 1 ;

        QueryAudioMixRequestDTO queryAudioMixRequestDTO = streamMediaConvert.convertQueryAudioMixRequest(request);
        log.info("查询混音信息交互参数:{}",queryAudioMixRequestDTO);
        QueryAudioMixResponseVO queryAudioMix = streamMediaInterface.queryAudioMix(ssid, queryAudioMixRequestDTO);
        log.info("查询混音信息应答信息:{}",queryAudioMix);
        return queryAudioMix;
    }

    @Override
    public StartVideoMixResponseVO startVideoMix(StartVideoMixRequest request) {
        log.info("开始画面合成入参信息:{}", request);
        Integer ssid = 1 ;

        String groupId = UUID.randomUUID().toString().replace("-","");
        StartVideoMixRequestDTO startVideoMixRequestDTO = streamMediaConvert.convertStartVideoMixRequest(request);
        startVideoMixRequestDTO.setGroupID(groupId);
        log.info("开始画面合成交互参数:{}",startVideoMixRequestDTO);
        String startVideoMix = streamMediaInterface.startVideoMix(ssid, startVideoMixRequestDTO);
        StartVideoMixResponseVO response = new StartVideoMixResponseVO();
        response.setMixID(startVideoMix);
        response.setGroupID(groupId);
        log.info("开始画面合成应答信息:{}",response);
        return response;
    }

    @Override
    public Boolean stopVideoMix(StopVideoMixRequest request) {
        log.info("停止画面合成入参信息:{}", request);
        Integer ssid = 1 ;

        StopVideoMixRequestDTO stopVideoMixRequestDTO = streamMediaConvert.convertStopVideoMixRequest(request);
        log.info("停止画面合成交互参数:{}",stopVideoMixRequestDTO);
        Boolean stopVideoMix = streamMediaInterface.stopVideoMix(ssid, stopVideoMixRequestDTO);
        log.info("停止画面合成应答信息:{}",stopVideoMix);
        return stopVideoMix;
    }

    @Override
    public Boolean updateVideoMix(UpdateVideoMixRequest request) {
        log.info("更新画面合成入参信息:{}", request);
        Integer ssid = 1 ;

        UpdateVideoMixRequestDTO updateVideoMixRequestDTO = streamMediaConvert.convertUpdateVideoMixRequest(request);
        log.info("更新画面合成交互参数:{}",updateVideoMixRequestDTO);
        Boolean updateVideoMix = streamMediaInterface.updateVideoMix(ssid, updateVideoMixRequestDTO);
        log.info("更新画面合成应答信息:{}",updateVideoMix);
        return updateVideoMix;
    }

    @Override
    public List<String> queryAllVideoMix(String unitId) {
        log.info("查询所有画面合成入参信息 :{}", unitId);
        Integer ssid = 1 ;

        List<String> queryAllVideoMix = streamMediaInterface.queryAllVideoMix(ssid);
        log.info("查询所有画面合成应答信息:{}",queryAllVideoMix);
        return queryAllVideoMix;
    }

    @Override
    public QueryVideoMixResponseVO queryVideoMix(QueryVideoMixRequest request) {
        log.info("查询画面信息入参信息:{}", request);
        Integer ssid = 1 ;

        QueryVideoMixRequestDTO queryVideoMixRequestDTO = streamMediaConvert.convertQueryVideoMixRequest(request);
        log.info("查询画面信息交互参数:{}",queryVideoMixRequestDTO);
        QueryVideoMixResponseVO queryVideoMix = streamMediaInterface.queryVideoMix(ssid, queryVideoMixRequestDTO);
        log.info("查询画面信息应答信息:{}",queryVideoMix);
        return queryVideoMix;
    }
}
