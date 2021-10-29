package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.DeviceException;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/19
 */
public class AiBoxException extends DeviceException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public AiBoxException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public AiBoxException(String errorMessage) {
        this.code = null;
        this.message = errorMessage;
    }

}
