package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;
import com.kedacom.device.core.constant.DeviceErrorEnum;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/1
 */
public class MtException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public MtException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public MtException(DeviceErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMsg();
    }

}
