package com.kedacom.device.core.service.impl;

import com.kedacom.avIntegration.request.tvplay.*;
import com.kedacom.avIntegration.response.tvplay.*;
import com.kedacom.device.core.data.DeviceConstants;
import com.kedacom.device.core.data.DeviceErrorEnum;
import com.kedacom.device.core.exception.TvPlayException;
import com.kedacom.device.core.msp.TvPlayManageSdk;
import com.kedacom.device.core.service.TvPlayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:49
 */
@Service
@Slf4j
public class TvPlayServiceImpl implements TvPlayService {

    @Resource
    private TvPlayManageSdk tvPlayManageSdk;

    @Override
    public BatchStartResponse batchStart(BatchStartRequest request) {
        log.info("窗口显示入参---BatchStartRequest:{}", request);
        BatchStartResponse response = tvPlayManageSdk.batchStart(request);
        log.info("窗口显示应答---BatchStartResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvPlayException(DeviceErrorEnum.TVPLAY_START_FAILED.getCode(), DeviceErrorEnum.TVPLAY_START_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public BatchStopResponse batchStop(BatchStopRequest request) {
        log.info("关闭窗口显示入参---BatchStopRequest:{}", request);
        BatchStopResponse response = tvPlayManageSdk.batchStop(request);
        log.info("关闭窗口显示应答---BatchStopResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvPlayException(DeviceErrorEnum.TVPLAY_STOP_FAILED.getCode(), DeviceErrorEnum.TVPLAY_STOP_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvPlayClearResponse clear(TvPlayClearRequest request) {
        log.info("清空窗口显示入参---TvPlayClearRequest:{}", request);
        TvPlayClearResponse response = tvPlayManageSdk.clear(request);
        log.info("清空窗口显示应答---TvPlayClearResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvPlayException(DeviceErrorEnum.TVPLAY_CLEAR_FAILED.getCode(), DeviceErrorEnum.TVPLAY_CLEAR_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvPlayStyleResponse style(TvPlayStyleRequest request) {
        log.info("设置窗口风格入参---TvPlayStyleRequest:{}", request);
        TvPlayStyleResponse response = tvPlayManageSdk.style(request);
        log.info("设置窗口风格应答---TvPlayStyleResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvPlayException(DeviceErrorEnum.TVPLAY_STYLE_FAILED.getCode(), DeviceErrorEnum.TVPLAY_STYLE_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvPlayOpenResponse open(TvPlayOpenRequest request) {
        log.info("任意开窗入参---TvPlayOpenRequest:{}", request);
        TvPlayOpenResponse response = tvPlayManageSdk.open(request);
        log.info("任意开窗应答---TvPlayOpenResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvPlayException(DeviceErrorEnum.TVPLAY_OPEN_FAILED.getCode(), DeviceErrorEnum.TVPLAY_OPEN_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvPlayOrderResponse order(TvPlayOrderRequest request) {
        log.info("窗口排序入参---TvPlayOrderRequest:{}", request);
        TvPlayOrderResponse response = tvPlayManageSdk.order(request);
        log.info("窗口排序应答---TvPlayOrderResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvPlayException(DeviceErrorEnum.TVPLAY_ORDER_FAILED.getCode(), DeviceErrorEnum.TVPLAY_ORDER_FAILED.getMsg());
        }
        return response;
    }

    @Override
    public TvPlayActionResponse action(TvPlayActionRequest request) {
        log.info("窗口操作入参---TvPlayActionRequest:{}", request);
        TvPlayActionResponse response = tvPlayManageSdk.action(request);
        log.info("窗口操作应答---TvPlayActionResponse:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            throw new TvPlayException(DeviceErrorEnum.TVPLAY_ACTION_FAILED.getCode(), DeviceErrorEnum.TVPLAY_ACTION_FAILED.getMsg());
        }
        return response;
    }
}
