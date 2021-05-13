package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;

/**
 * 流媒体服务异常
 *
 * @Auther: hxj
 * @Date: 2021/5/13 18:52
 */
public class StreamMediaException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public StreamMediaException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
