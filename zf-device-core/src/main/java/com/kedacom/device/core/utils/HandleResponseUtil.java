package com.kedacom.device.core.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.exception.*;
import com.kedacom.device.cu.CuResponse;
import com.kedacom.device.mp.MpResponse;
import com.kedacom.device.svr.SvrResponse;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
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
    private MspErrCode mspErrCode;

    @Autowired
    private KmErrCode kmErrCode;

    @Autowired
    private McuErrCode mcuErrCode;


    /**
     * 处理拼控服务响应消息
     *
     * @param str
     * @param errorEnum
     * @param errCode
     * @param errMsg
     */
    public void handleMSPRes(String str, DeviceErrorEnum errorEnum, Integer errCode, String errMsg) {
        if (ObjectUtil.notEqual(errCode, DeviceConstants.SUCCESS)) {
            if (StrUtil.isNotBlank(errMsg)) {
                log.error(str, errCode, errorEnum.getCode(), errMsg);
                throw new MspException(errorEnum.getCode(), errMsg);
            } else if (StrUtil.isNotBlank(mspErrCode.matchErrMsg(errCode))) {
                log.error(str, errCode, errorEnum.getCode(), mspErrCode.matchErrMsg(errCode));
                throw new MspException(errorEnum.getCode(), mspErrCode.matchErrMsg(errCode));
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
        if (ObjectUtil.notEqual(res.acquireErrcode(), DeviceConstants.SUCCESS)) {
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
        if (ObjectUtil.notEqual(res.acquireErrcode(), DeviceConstants.SUCCESS)) {
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
     *
     * @param str
     * @param errorEnum
     * @param res
     */
    public void handleOperateRes(String str, DeviceErrorEnum errorEnum, BaseResponse res) {
        if (ObjectUtil.notEqual(res.acquireErrcode(), DeviceConstants.SUCCESS)) {
            if (StrUtil.isNotBlank(kmErrCode.matchErrMsg(res.acquireErrcode()))) {
                log.error(str, res.acquireErrcode(), errorEnum.getCode(), kmErrCode.matchErrMsg(res.acquireErrcode()));
                throw new UmsOperateException(errorEnum.getCode(), kmErrCode.matchErrMsg(res.acquireErrcode()));
            } else {
                log.error(str, res.acquireErrcode(), errorEnum.getCode(), errorEnum.getMsg());
                throw new UmsOperateException(errorEnum.getCode(), errorEnum.getMsg());
            }
        }
    }

    /**
     * 处理会议平台异常
     *
     * @param str
     * @param errorEnum
     * @param res
     */
    public void handleMpRes(String str, DeviceErrorEnum errorEnum, MpResponse res) {
        if (ObjectUtil.notEqual(res.getCode(), DeviceConstants.SUCCESS)) {
            if (StrUtil.isNotBlank(mcuErrCode.matchErrMsg(res.getCode()))) {
                log.error(str, res.getCode(), errorEnum.getCode(), mcuErrCode.matchErrMsg(res.getCode()));
                throw new MpException(errorEnum.getCode(), mcuErrCode.matchErrMsg(res.getCode()));
            } else {
                log.error(str, res.getCode(), errorEnum.getCode(), errorEnum.getMsg());
                throw new MpException(errorEnum.getCode(), errorEnum.getMsg());
            }
        }
    }

    /**
     * 处理SVR异常
     *
     * @param str
     * @param errorEnum
     * @param res
     */
    public void handleSvrRes(String str, DeviceErrorEnum errorEnum, SvrResponse res) {
        if (ObjectUtil.notEqual(res.getCode(), DeviceConstants.SUCCESS)) {
                log.error(str, res.getCode(), errorEnum.getCode(), errorEnum.getMsg());
                throw new SvrException(res.getCode(), errorEnum.getMsg());
        }
    }

    /**
     * 处理cu异常
     *
     * @param str
     * @param errorEnum
     * @param res
     */
    public void handleCuRes(String str, DeviceErrorEnum errorEnum, CuResponse res) {
        if (ObjectUtil.notEqual(res.getCode(), DeviceConstants.SUCCESS)) {
            log.error(str, res.getCode(), errorEnum.getCode(), errorEnum.getMsg());
            throw new CuException(res.getCode(), errorEnum.getMsg());
        }
    }

    /**
     * 处理会议平台基本错误
     *
     * @param entity
     */
    public void handleMp(UmsMcuEntity entity) {
        if (ObjectUtil.isNull(entity)) {
            throw new MpException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        if (StrUtil.isBlank(entity.getSsid())) {
            throw new MpException(DeviceErrorEnum.DEVICE_NOT_LOGIN);
        }
    }

}
