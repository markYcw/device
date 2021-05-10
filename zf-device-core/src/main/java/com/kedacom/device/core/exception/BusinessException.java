package com.kedacom.device.core.exception;

import com.kedacom.device.core.data.DeviceErrorEnum;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 16:08
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(DeviceErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }

}
