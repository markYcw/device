package com.kedacom.device.core.data;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:09
 */
public enum DeviceErrorEnum {

    PARAM_ERROR(1001, ""),

    STREAM_MEDIA_FAILED(2001, "流媒体服务失败"),

    SYSTEM_AUTH_FAILED(2002, "鉴权操作失败"),

    TV_WALL_FAILED(2003, "大屏管理操作失败"),

    TV_PLAY_FAILED(2004, "窗口浏览操作失败"),

    DECODER_OSD_FAILED(2005, "解码通道操作失败"),

    SCHEME_FAILED(2006, "预案布局操作失败"),

    MSP_REMOTE_ERROR(3001, "调用远程接口错误");


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
