package com.kedacom.device.core.utils;

import cn.hutool.core.util.StrUtil;
import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.device.core.config.AvIntegrationErrCode;
import com.kedacom.device.core.config.KmErrCode;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.exception.MspException;
import com.kedacom.device.core.exception.StreamMediaException;
import com.kedacom.device.core.exception.UmsNotifyException;
import com.kedacom.device.core.exception.UmsOperateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: hxj
 * @Date: 2021/5/20 14:45
 */
@Component
@Slf4j
public class HandleResponseUtil {

    @Autowired
    private AvIntegrationErrCode integrationErrCode;

    @Autowired
    private KmErrCode kmErrCode;

    /**
     * 处理拼控服务响应消息
     *
     * @param str
     * @param errorEnum
     * @param errCode
     * @param errMsg
     */
    public void handleMSPRes(String str, DeviceErrorEnum errorEnum, Integer errCode, String errMsg) {
        if (errCode != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(errMsg)) {
                log.error(str, errCode, errorEnum.getCode(), errMsg);
                throw new MspException(errorEnum.getCode(), errMsg);
            } else if (StrUtil.isNotBlank(integrationErrCode.matchErrMsg(errCode))) {
                log.error(str, errCode, errorEnum.getCode(), integrationErrCode.matchErrMsg(errCode));
                throw new MspException(errorEnum.getCode(), integrationErrCode.matchErrMsg(errCode));
            } else {
                log.error(str, errCode, errorEnum.getCode(), errorEnum.getMsg());
                throw new MspException(errorEnum.getCode(), errorEnum.getMsg());
            }
        }
    }

    /**
     * 处理流媒体服务响应消息
     *
     * @param str
     * @param errorEnum
     * @param res
     */
    public void handleSMSRes(String str, DeviceErrorEnum errorEnum, BaseResponse res) {
        if (res.acquireErrcode() != DeviceConstants.SUCCESS) {
            if (StrUtil.isNotBlank(kmErrCode.matchErrMsg(res.acquireErrcode()))) {
                log.error(str, res.acquireErrcode(), errorEnum.getCode(), kmErrCode.matchErrMsg(res.acquireErrcode()));
                throw new StreamMediaException(errorEnum.getCode(), kmErrCode.matchErrMsg(res.acquireErrcode()));
            } else {
                log.error(str, res.acquireErrcode(), errorEnum.getCode(), errorEnum.getMsg());
                throw new StreamMediaException(errorEnum.getCode(), errorEnum.getMsg());
            }
        }
    }

    /**
     * 处理ums通知异常
     *
     * @param str
     * @param errorEnum
     * @param res
     */
    public void handleUMSNotifyRes(String str, DeviceErrorEnum errorEnum, BaseResponse res) {
        if (!res.acquireErrcode().equals(DeviceConstants.SUCCESS)) {
            if (StrUtil.isNotBlank(kmErrCode.matchErrMsg(res.acquireErrcode()))) {
                log.error(str, res.acquireErrcode(), errorEnum.getCode(), kmErrCode.matchErrMsg(res.acquireErrcode()));
                throw new UmsNotifyException(errorEnum.getCode(), kmErrCode.matchErrMsg(res.acquireErrcode()));
            } else {
                log.error(str, res.acquireErrcode(), errorEnum.getCode(), errorEnum.getMsg());
                throw new UmsNotifyException(errorEnum.getCode(), errorEnum.getMsg());
            }
        }
    }

    /**
     * 处理 融合调度 异常
     * @param str
     * @param errorEnum
     * @param res
     */
    public void handleOperateRes(String str, DeviceErrorEnum errorEnum, BaseResponse res) {
        if (!res.acquireErrcode().equals(DeviceConstants.SUCCESS)) {
            if (StrUtil.isNotBlank(kmErrCode.matchErrMsg(res.acquireErrcode()))) {
                log.error(str, res.acquireErrcode(), errorEnum.getCode(), kmErrCode.matchErrMsg(res.acquireErrcode()));
                throw new UmsOperateException(errorEnum.getCode(), kmErrCode.matchErrMsg(res.acquireErrcode()));
            } else {
                log.error(str, res.acquireErrcode(), errorEnum.getCode(), errorEnum.getMsg());
                throw new UmsOperateException(errorEnum.getCode(), errorEnum.getMsg());
            }
        }
    }
}
