package com.kedacom.device.core.exception;

import com.kedacom.device.common.exception.OperatorException;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
public class UmsOperateException extends OperatorException {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public UmsOperateException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
