package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;

/**
 * 解码通道服务异常
 *
 * @Auther: hxj
 * @Date: 2021/5/11 13:24
 */
public class DecoderException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public DecoderException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
