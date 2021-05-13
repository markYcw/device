package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.kedacom.acl.network.data.avIntegration.tvplay.*;
import com.kedacom.avIntegration.request.tvplay.*;
import com.kedacom.device.core.config.AvIntegrationErrCode;
import com.kedacom.device.core.data.DeviceConstants;
import com.kedacom.device.core.data.DeviceErrorEnum;
import com.kedacom.device.core.exception.TvPlayException;
import com.kedacom.device.core.msp.TvPlayManageSdk;
import com.kedacom.device.core.service.TvPlayService;
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
    private AvIntegrationErrCode avIntegrationErrCode;

    @Override
    public void batchStart(BatchStartRequest request) {
        log.info("窗口显示入参:{}", request);
        BatchStartResponse response = tvPlayManageSdk.batchStart(request);
        log.info("窗口显示应答:{}", response);
        handleRes("窗口显示异常:{},{}", response.getError(), null);
    }

    @Override
    public void batchStop(BatchStopRequest request) {
        log.info("关闭窗口显示入参:{}", request);
        BatchStopResponse response = tvPlayManageSdk.batchStop(request);
        log.info("关闭窗口显示应答:{}", response);
        handleRes("关闭窗口显示异常:{},{}", response.getError(), null);
    }

    @Override
    public void clear(TvPlayClearRequest request) {
        log.info("清空窗口显示入参:{}", request);
        TvPlayClearResponse response = tvPlayManageSdk.clear(request);
        log.info("清空窗口显示应答:{}", response);
        handleRes("清空窗口显示异常:{},{}", response.getError(), null);
    }

    @Override
    public void style(TvPlayStyleRequest request) {
        log.info("设置窗口风格入参:{}", request);
        TvPlayStyleResponse response = tvPlayManageSdk.style(request);
        log.info("设置窗口风格应答:{}", response);
        handleRes("设置窗口风格异常:{},{}", response.getError(), null);
    }

    @Override
    public TvPlayOpenResponse open(TvPlayOpenRequest request) {
        log.info("任意开窗入参:{}", request);
        TvPlayOpenResponse response = tvPlayManageSdk.open(request);
        log.info("任意开窗应答:{}", response);
        handleRes("任意开窗异常:{},{}", response.getError(), null);
        return response;
    }

    @Override
    public void order(TvPlayOrderRequest request) {
        log.info("窗口排序入参:{}", request);
        TvPlayOrderResponse response = tvPlayManageSdk.order(request);
        log.info("窗口排序应答:{}", response);
        handleRes("窗口排序异常:{},{}", response.getError(), null);
    }

    @Override
    public void action(TvPlayActionRequest request) {
        log.info("窗口操作入参:{}", request);
        TvPlayActionResponse response = tvPlayManageSdk.action(request);
        log.info("窗口操作应答:{}", response);
        handleRes("窗口操作异常:{},{}", response.getError(), null);
    }

    private void handleRes(String str, Integer errCode, String errorMsg) {
        if (errCode != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(errorMsg)) {
                log.error(str, DeviceErrorEnum.TV_PLAY_FAILED.getCode(), errorMsg);
                throw new TvPlayException(DeviceErrorEnum.TV_PLAY_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(errCode));
            } else if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(errCode))) {
                log.error(str, DeviceErrorEnum.TV_PLAY_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(errCode));
                throw new TvPlayException(DeviceErrorEnum.TV_PLAY_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(errCode));
            } else {
                log.error(str, DeviceErrorEnum.TV_PLAY_FAILED.getCode(), DeviceErrorEnum.TV_PLAY_FAILED.getMsg());
                throw new TvPlayException(DeviceErrorEnum.TV_PLAY_FAILED.getCode(), DeviceErrorEnum.TV_PLAY_FAILED.getMsg());
            }
        }
    }
}
