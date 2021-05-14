package com.kedacom.device.advice;

import com.kedacom.BaseResult;
import com.kedacom.device.common.exception.AuthException;
import com.kedacom.device.common.exception.ParamException;
import com.kedacom.device.core.data.DeviceErrorEnum;
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
     * 鉴权异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({AuthException.class})
    public BaseResult handleException(AuthException e) {
        return BaseResult.failed(e.getCode(), e.getMessage());
    }

    /**
     * 大屏异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({TvWallException.class})
    public BaseResult handleException(TvWallException e) {
        return BaseResult.failed(e.getCode(), e.getMessage());
    }

    /**
     * 窗口浏览异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({TvPlayException.class})
    public BaseResult handleException(TvPlayException e) {
        return BaseResult.failed(e.getCode(), e.getMessage());
    }

    /**
     * 预案布局异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({SchemeException.class})
    public BaseResult handleException(SchemeException e) {
        return BaseResult.failed(e.getCode(), e.getMessage());
    }

    /**
     * 解码通道异常捕获
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({DecoderException.class})
    public BaseResult handleException(DecoderException e) {
        return BaseResult.failed(e.getCode(), e.getMessage());
    }

}
