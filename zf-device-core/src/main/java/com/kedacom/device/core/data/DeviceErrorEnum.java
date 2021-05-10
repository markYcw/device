package com.kedacom.device.core.data;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:09
 */
public enum DeviceErrorEnum {

    PARAM_ERROR(1001,""),
    MSP_REMOTE_ERROR(3001, "调用远程接口错误"),

    SYSTEM_LOGIN_FAILED(3002, "登录显控服务获取令牌失败"),

    SYSTEM_KEEPALIVE_FAILED(3003, "token令牌保活失败"),

    SYSTEM_VERSION_FAILED(3004, "获取版本号失败"),

    SYSTEM_LOGOUT_FAILED(3005, "退出显控服务失败");


    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    DeviceErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
