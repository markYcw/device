package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;
import com.kedacom.device.core.constant.DeviceErrorEnum;

/**
 * @author ycw
 * @date: 2021/11/01 15:54
 * @description cu自定义异常
 */
public class CuException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public CuException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public CuException(DeviceErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMsg();
    }
}
