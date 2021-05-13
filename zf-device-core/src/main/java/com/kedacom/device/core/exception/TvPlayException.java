package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;

/**
 * 窗口浏览异常异常
 *
 * @Auther: hxj
 * @Date: 2021/5/10 17:33
 */
public class TvPlayException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public TvPlayException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
