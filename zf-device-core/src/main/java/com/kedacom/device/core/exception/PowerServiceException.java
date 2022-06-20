package com.kedacom.device.core.exception;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kedacom.power.model.KmResultCodeEnum;

/**
 * @author hexijian
 */
public class PowerServiceException extends RuntimeException {

    private Integer code;
    private String ErrorMsg;

    //private static final long serialVersionUID = 661008332101329147L;

    public Integer getCode() {
        return code;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public PowerServiceException(KmResultCodeEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.ErrorMsg = error.getMessage();
    }

    public PowerServiceException(KmResultCodeEnum error, String message) {
        super(StringUtils.isBlank(message) ? message : error.getMessage());
        this.code = error.getCode();
        this.ErrorMsg = message;
    }

    public PowerServiceException(Integer code, String errorMsg, String message) {
        super(message);
        this.code = code;
        this.ErrorMsg = errorMsg;
    }

    public PowerServiceException(Integer code, String errorMsg) {
        this.code = code;
        this.ErrorMsg = errorMsg;
    }

}
