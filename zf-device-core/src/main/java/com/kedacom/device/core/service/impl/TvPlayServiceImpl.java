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
        if (response.getError() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(response.getError()))) {
                log.error("窗口显示应答异常:{},{}", DeviceErrorEnum.TVPLAY_START_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_START_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
            } else {
                log.error("窗口显示应答异常:{},{}", DeviceErrorEnum.TVPLAY_START_FAILED.getCode(), DeviceErrorEnum.TVPLAY_START_FAILED.getMsg());
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_START_FAILED.getCode(), DeviceErrorEnum.TVPLAY_START_FAILED.getMsg());
            }
        }
    }

    @Override
    public void batchStop(BatchStopRequest request) {
        log.info("关闭窗口显示入参:{}", request);
        BatchStopResponse response = tvPlayManageSdk.batchStop(request);
        log.info("关闭窗口显示应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(response.getError()))) {
                log.error("关闭窗口显示应答异常:{},{}", DeviceErrorEnum.TVPLAY_STOP_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_STOP_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
            } else {
                log.error("关闭窗口显示应答异常:{},{}", DeviceErrorEnum.TVPLAY_STOP_FAILED.getCode(), DeviceErrorEnum.TVPLAY_STOP_FAILED.getMsg());
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_STOP_FAILED.getCode(), DeviceErrorEnum.TVPLAY_STOP_FAILED.getMsg());
            }
        }
    }

    @Override
    public void clear(TvPlayClearRequest request) {
        log.info("清空窗口显示入参:{}", request);
        TvPlayClearResponse response = tvPlayManageSdk.clear(request);
        log.info("清空窗口显示应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(response.getError()))) {
                log.error("清空窗口显示应答异常:{},{}", DeviceErrorEnum.TVPLAY_CLEAR_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_CLEAR_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
            } else {
                log.error("清空窗口显示应答异常:{},{}", DeviceErrorEnum.TVPLAY_CLEAR_FAILED.getCode(), DeviceErrorEnum.TVPLAY_CLEAR_FAILED.getMsg());
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_CLEAR_FAILED.getCode(), DeviceErrorEnum.TVPLAY_CLEAR_FAILED.getMsg());
            }
        }
    }

    @Override
    public void style(TvPlayStyleRequest request) {
        log.info("设置窗口风格入参:{}", request);
        TvPlayStyleResponse response = tvPlayManageSdk.style(request);
        log.info("设置窗口风格应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(response.getError()))) {
                log.error("设置窗口风格应答异常:{},{}", DeviceErrorEnum.TVPLAY_STYLE_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_STYLE_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
            } else {
                log.error("设置窗口风格应答异常:{},{}", DeviceErrorEnum.TVPLAY_STYLE_FAILED.getCode(), DeviceErrorEnum.TVPLAY_STYLE_FAILED.getMsg());
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_STYLE_FAILED.getCode(), DeviceErrorEnum.TVPLAY_STYLE_FAILED.getMsg());
            }
        }
    }

    @Override
    public TvPlayOpenResponse open(TvPlayOpenRequest request) {
        log.info("任意开窗入参:{}", request);
        TvPlayOpenResponse response = tvPlayManageSdk.open(request);
        log.info("任意开窗应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(response.getError()))) {
                log.error("任意开窗应答异常:{},{}", DeviceErrorEnum.TVPLAY_OPEN_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_OPEN_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
            } else {
                log.error("任意开窗应答异常:{},{}", DeviceErrorEnum.TVPLAY_OPEN_FAILED.getCode(), DeviceErrorEnum.TVPLAY_OPEN_FAILED.getMsg());
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_OPEN_FAILED.getCode(), DeviceErrorEnum.TVPLAY_OPEN_FAILED.getMsg());
            }
        }
        return response;
    }

    @Override
    public void order(TvPlayOrderRequest request) {
        log.info("窗口排序入参:{}", request);
        TvPlayOrderResponse response = tvPlayManageSdk.order(request);
        log.info("窗口排序应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(response.getError()))) {
                log.error("窗口排序应答异常:{},{}", DeviceErrorEnum.TVPLAY_ORDER_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()))
                ;
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_ORDER_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
            } else {
                log.error("窗口排序应答异常:{},{}", DeviceErrorEnum.TVPLAY_ORDER_FAILED.getCode(), DeviceErrorEnum.TVPLAY_ORDER_FAILED.getMsg());
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_ORDER_FAILED.getCode(), DeviceErrorEnum.TVPLAY_ORDER_FAILED.getMsg());
            }
        }
    }

    @Override
    public void action(TvPlayActionRequest request) {
        log.info("窗口操作入参:{}", request);
        TvPlayActionResponse response = tvPlayManageSdk.action(request);
        log.info("窗口操作应答:{}", response);
        if (response.getError() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(avIntegrationErrCode.matchErrMsg(response.getError()))) {
                log.error("窗口操作应答异常:{},{}", DeviceErrorEnum.TVPLAY_ACTION_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_ACTION_FAILED.getCode(), avIntegrationErrCode.matchErrMsg(response.getError()));
            } else {
                log.error("窗口操作应答异常:{},{}", DeviceErrorEnum.TVPLAY_ACTION_FAILED.getCode(), DeviceErrorEnum.TVPLAY_ACTION_FAILED.getMsg());
                throw new TvPlayException(DeviceErrorEnum.TVPLAY_ACTION_FAILED.getCode(), DeviceErrorEnum.TVPLAY_ACTION_FAILED.getMsg());
            }
        }
    }
}
