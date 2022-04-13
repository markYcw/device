package com.kedacom.device.core.exception;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kedacom.power.model.KmResultCodeEnum;

public class KmServiceException extends RuntimeException {

    private Integer code;
    private String ErrorMsg;

    //private static final long serialVersionUID = 661008332101329147L;

    public Integer getCode() {
        return code;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public KmServiceException(KmResultCodeEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.ErrorMsg = error.getMessage();
    }

    public KmServiceException(KmResultCodeEnum error, String message) {
        super(StringUtils.isBlank(message) ? message : error.getMessage());
        this.code = error.getCode();
        this.ErrorMsg = message;
    }

    public KmServiceException(Integer code, String errorMsg, String message) {
        super(message);
        this.code = code;
        this.ErrorMsg = errorMsg;
    }

    public KmServiceException(Integer code, String errorMsg) {
        this.code = code;
        this.ErrorMsg = errorMsg;
    }

}
