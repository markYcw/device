package com.kedacom.device.service.impl;

import com.kedacom.BaseResult;
import com.kedacom.acl.network.unite.StreamMediaInterface;
import com.kedacom.device.service.StreamMediaService;
import com.kedacom.streamMedia.request.QueryrecRequestDTO;
import com.kedacom.streamMedia.request.StartrecRequestDTO;
import com.kedacom.streamMedia.request.StoprecRequestDTO;
import com.kedacom.streamMedia.response.QueryrecResponseVO;
import com.kedacom.streamMedia.response.StartrecResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public BaseResult<StartrecResponseVO> startrec(StartrecRequestDTO startrecRequestDTO) {
        log.info("开启录像入参信息 —— startrecDTO:{}", startrecRequestDTO);
        String ssid = "";
        BaseResult<StartrecResponseVO> startrec = streamMediaInterface.startrec(ssid, startrecRequestDTO);
        log.info("开启录像应答信息 —— StartrecResponseVO:{}",startrec);
        return startrec;
    }

    @Override
    public BaseResult stoprec(StoprecRequestDTO stoprecRequestDTO) {
        log.info("停止录像入参信息 —— stoprecRequestDTO:{}", stoprecRequestDTO);
        String ssid = "";
        BaseResult stoprec = streamMediaInterface.stoprec(ssid, stoprecRequestDTO);
        log.info("停止录像应答信息 —— stoprec:{}",stoprec);
        return stoprec;
    }

    @Override
    public BaseResult<QueryrecResponseVO> queryrec(QueryrecRequestDTO queryrecRequestDTO) {
        log.info("查询录像记录入参信息 —— queryrecRequestDTO:{}", queryrecRequestDTO);
        String ssid = "";
        BaseResult<QueryrecResponseVO> queryrec = streamMediaInterface.queryrec(ssid, queryrecRequestDTO);
        log.info("查询录像记录应答信息 —— stoprec:{}",queryrec);
        return queryrec;
    }

}
