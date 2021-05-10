package com.kedacom.device.advice;

import com.kedacom.BaseResult;
import com.kedacom.device.core.data.DeviceErrorEnum;
import com.kedacom.device.core.exception.BusinessException;
import com.kedacom.device.core.exception.ParamException;
import com.kedacom.device.core.msp.exception.MspRemoteCallException;
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

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BusinessException.class})
    public BaseResult handleException(BusinessException e) {
        return BaseResult.failed(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MspRemoteCallException.class})
    public BaseResult handleException(MspRemoteCallException e) {
        return BaseResult.failed(DeviceErrorEnum.MSP_REMOTE_ERROR.getCode(), DeviceErrorEnum.MSP_REMOTE_ERROR.getMsg());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({ParamException.class})
    public BaseResult handleException(ParamException e) {
        return BaseResult.failed(DeviceErrorEnum.PARAM_ERROR.getCode(), e.getMessage());
    }

}
