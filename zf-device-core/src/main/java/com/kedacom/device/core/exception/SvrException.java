package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;
import com.kedacom.device.core.constant.DeviceErrorEnum;

/**
 * @author ycw
 * @date: 2021/09/06 15:54
 * @description svr自定义异常
 */
public class SvrException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public SvrException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public SvrException(DeviceErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMsg();
    }
}
