package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;
import com.kedacom.device.core.constant.DeviceErrorEnum;

/**
 * @author ycw
 * @date: 2022/04/00 15:54
 * @description newMedia自定义异常
 */
public class NewMediaException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public NewMediaException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public NewMediaException(DeviceErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMsg();
    }
}
