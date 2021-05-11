package com.kedacom.device.common.exception;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 17:32
 */
public class AuthException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
