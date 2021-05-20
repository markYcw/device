package com.kedacom.device.core.service.impl;

import com.kedacom.acl.network.data.avIntegration.tvplay.*;
import com.kedacom.avIntegration.request.tvplay.*;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.msp.TvPlayManageSdk;
import com.kedacom.device.core.service.TvPlayService;
import com.kedacom.device.core.utils.HandleResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:49
 */
@Service
@Slf4j
public class TvPlayServiceImpl implements TvPlayService {

    @Autowired
    private TvPlayManageSdk tvPlayManageSdk;
    @Autowired
    private HandleResponseUtil responseUtil;


    @Override
    public void batchStart(BatchStartRequest request) {
        log.info("窗口显示入参:{}", request);
        BatchStartResponse response = tvPlayManageSdk.batchStart(request);
        log.info("窗口显示应答:{}", response);
        responseUtil.handleMSPRes("窗口显示异常:{},{},{}", DeviceErrorEnum.TV_PLAY_START_FAILED, response.getError(), null);
    }

    @Override
    public void batchStop(BatchStopRequest request) {
        log.info("关闭窗口显示入参:{}", request);
        BatchStopResponse response = tvPlayManageSdk.batchStop(request);
        log.info("关闭窗口显示应答:{}", response);
        responseUtil.handleMSPRes("关闭窗口显示异常:{},{},{}", DeviceErrorEnum.TV_PLAY_STOP_FAILED, response.getError(), null);
    }

    @Override
    public void clear(TvPlayClearRequest request) {
        log.info("清空窗口显示入参:{}", request);
        TvPlayClearResponse response = tvPlayManageSdk.clear(request);
        log.info("清空窗口显示应答:{}", response);
        responseUtil.handleMSPRes("清空窗口显示异常:{},{},{}", DeviceErrorEnum.TV_PLAY_CLEAR_FAILED, response.getError(), null);
    }

    @Override
    public void style(TvPlayStyleRequest request) {
        log.info("设置窗口风格入参:{}", request);
        TvPlayStyleResponse response = tvPlayManageSdk.style(request);
        log.info("设置窗口风格应答:{}", response);
        responseUtil.handleMSPRes("设置窗口风格异常:{},{},{}", DeviceErrorEnum.TV_PLAY_STYLE_FAILED, response.getError(), null);
    }

    @Override
    public TvPlayOpenResponse open(TvPlayOpenRequest request) {
        log.info("任意开窗入参:{}", request);
        TvPlayOpenResponse response = tvPlayManageSdk.open(request);
        log.info("任意开窗应答:{}", response);
        responseUtil.handleMSPRes("任意开窗异常:{},{},{}", DeviceErrorEnum.TV_PLAY_OPEN_FAILED, response.getError(), null);
        return response;
    }

    @Override
    public void order(TvPlayOrderRequest request) {
        log.info("窗口排序入参:{}", request);
        TvPlayOrderResponse response = tvPlayManageSdk.order(request);
        log.info("窗口排序应答:{}", response);
        responseUtil.handleMSPRes("窗口排序异常:{},{},{}", DeviceErrorEnum.TV_PLAY_ORDER_FAILED, response.getError(), null);
    }

    @Override
    public void action(TvPlayActionRequest request) {
        log.info("窗口操作入参:{}", request);
        TvPlayActionResponse response = tvPlayManageSdk.action(request);
        log.info("窗口操作应答:{}", response);
        responseUtil.handleMSPRes("窗口操作异常:{},{},{}", DeviceErrorEnum.TV_PLAY_ACTION_FAILED, response.getError(), null);
    }
}
