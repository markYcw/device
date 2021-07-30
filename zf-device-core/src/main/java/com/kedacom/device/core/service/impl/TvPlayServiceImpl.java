package com.kedacom.device.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.acl.network.data.avIntegration.tvplay.*;
import com.kedacom.msp.request.tvplay.*;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.service.TvPlayService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import com.kedacom.device.core.utils.RemoteRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:49
 */
@Service
@Slf4j
public class TvPlayServiceImpl implements TvPlayService {

    @Autowired
    private HandleResponseUtil responseUtil;

    @Autowired
    private RemoteRestTemplate remoteRestTemplate;

    @Value("${zf.msp.server_addr}")
    private String mspUrl;

    private static final String mspPath = "/api/v1/manage/tvplay/";


    @Override
    public void batchStart(BatchStartRequest request) {
        log.info("窗口显示入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "batch/start", JSON.toJSONString(request), String.class);
        BatchStartResponse startResponse = JSONObject.parseObject(response, BatchStartResponse.class);

        log.info("窗口显示应答:{}", startResponse);
        if (startResponse != null) {
            responseUtil.handleMSPRes("窗口显示异常:{},{},{}", DeviceErrorEnum.TV_PLAY_START_FAILED, startResponse.getError(), null);
        }
    }

    @Override
    public void batchStop(BatchStopRequest request) {
        log.info("关闭窗口显示入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "batch/stop", JSON.toJSONString(request), String.class);
        BatchStopResponse stopResponse = JSONObject.parseObject(response, BatchStopResponse.class);

        log.info("关闭窗口显示应答:{}", stopResponse);
        if (stopResponse != null) {
            responseUtil.handleMSPRes("关闭窗口显示异常:{},{},{}", DeviceErrorEnum.TV_PLAY_STOP_FAILED, stopResponse.getError(), null);
        }
    }

    @Override
    public void clear(TvPlayClearRequest request) {
        log.info("清空窗口显示入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "clear", JSON.toJSONString(request), String.class);
        TvPlayClearResponse clearResponse = JSONObject.parseObject(response, TvPlayClearResponse.class);

        log.info("清空窗口显示应答:{}", clearResponse);
        if (clearResponse != null) {
            responseUtil.handleMSPRes("清空窗口显示异常:{},{},{}", DeviceErrorEnum.TV_PLAY_CLEAR_FAILED, clearResponse.getError(), null);
        }
    }

    @Override
    public void style(TvPlayStyleRequest request) {
        log.info("设置窗口风格入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "style", JSON.toJSONString(request), String.class);
        TvPlayStyleResponse styleResponse = JSONObject.parseObject(response, TvPlayStyleResponse.class);

        log.info("设置窗口风格应答:{}", styleResponse);
        if (styleResponse != null) {
            responseUtil.handleMSPRes("设置窗口风格异常:{},{},{}", DeviceErrorEnum.TV_PLAY_STYLE_FAILED, styleResponse.getError(), null);
        }
    }

    @Override
    public TvPlayOpenResponse open(TvPlayOpenRequest request) {
        log.info("任意开窗入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "open", JSON.toJSONString(request), String.class);
        TvPlayOpenResponse openResponse = JSONObject.parseObject(response, TvPlayOpenResponse.class);

        log.info("任意开窗应答:{}", openResponse);
        if (openResponse != null) {
            responseUtil.handleMSPRes("任意开窗异常:{},{},{}", DeviceErrorEnum.TV_PLAY_OPEN_FAILED, openResponse.getError(), null);
        }
        return openResponse;
    }

    @Override
    public void order(TvPlayOrderRequest request) {
        log.info("窗口排序入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "order", JSON.toJSONString(request), String.class);
        TvPlayOrderResponse orderResponse = JSONObject.parseObject(response, TvPlayOrderResponse.class);

        log.info("窗口排序应答:{}", orderResponse);
        if (orderResponse != null) {
            responseUtil.handleMSPRes("窗口排序异常:{},{},{}", DeviceErrorEnum.TV_PLAY_ORDER_FAILED, orderResponse.getError(), null);
        }
    }

    @Override
    public void action(TvPlayActionRequest request) {
        log.info("窗口操作入参:{}", JSON.toJSONString(request));

        String response = remoteRestTemplate.getRestTemplate().postForObject(mspUrl + mspPath + "action", JSON.toJSONString(request), String.class);
        TvPlayActionResponse actionResponse = JSONObject.parseObject(response, TvPlayActionResponse.class);

        log.info("窗口操作应答:{}", actionResponse);
        if (actionResponse != null) {
            responseUtil.handleMSPRes("窗口操作异常:{},{},{}", DeviceErrorEnum.TV_PLAY_ACTION_FAILED, actionResponse.getError(), null);
        }
    }
}
