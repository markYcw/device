package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 17:33
 */
public class TvWallException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public TvWallException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
