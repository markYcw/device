package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 20:26
 */
public class SchemeException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public SchemeException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
