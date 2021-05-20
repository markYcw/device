package com.kedacom.device.advice;

import com.kedacom.BaseResult;
import com.kedacom.device.common.exception.ParamException;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 16:19
 */
@RestControllerAdvice
@Slf4j
public class DeviceAdvice {

    /**
     * 参数异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({ParamException.class})
    public BaseResult handleException(ParamException e) {
        return BaseResult.failed(DeviceErrorEnum.PARAM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 远程调用接口异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MspRemoteCallException.class})
    public BaseResult handleException(MspRemoteCallException e) {
        return BaseResult.failed(DeviceErrorEnum.MSP_REMOTE_ERROR.getCode(), DeviceErrorEnum.MSP_REMOTE_ERROR.getMsg());
    }

    /**
     * 解码通道异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MspException.class})
    public BaseResult handleException(MspException e) {
        return BaseResult.failed(e.getCode(), e.getMessage());
    }

    /**
     * 流媒体异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({StreamMediaException.class})
    public BaseResult handleException(StreamMediaException e) {
        return BaseResult.failed(e.getCode(), e.getMessage());
    }


    /**
     * ums异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({UmsNotifyException.class})
    public BaseResult handleException(UmsNotifyException e) {
        return BaseResult.failed(e.getCode(), e.getMessage());
    }


    /**
     * ums异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({UmsManagerException.class})
    public BaseResult handleException(UmsManagerException e) {
        return BaseResult.failed(DeviceErrorEnum.UMS_SERVICE_ERROR.getCode(), DeviceErrorEnum.UMS_SERVICE_ERROR.getMsg());
    }


    /**
     * 统一设备异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    public BaseResult handleException(Exception e) {
        return BaseResult.failed(DeviceErrorEnum.UMS_SERVICE_ERROR.getCode(), DeviceErrorEnum.UMS_SERVICE_ERROR.getMsg());
    }

}
