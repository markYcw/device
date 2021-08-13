package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;
import com.kedacom.device.core.constant.DeviceErrorEnum;

/**
 * @author hxj
 * @date: 2021/8/13 15:54
 * @description 会议平台异常
 */
public class MpException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public MpException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public MpException(DeviceErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMsg();
    }
}
