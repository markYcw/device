package com.kedacom.device.core.data;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:09
 */
public enum DeviceErrorEnum {

    PARAM_ERROR(1001, ""),

    STREAM_MEDIA_FAILED(2010, "流媒体服务失败"),


    MSP_REMOTE_ERROR(3001, "调用拼控服务远程接口错误"),

    SYSTEM_AUTH_FAILED(3002, "鉴权操作失败"),
    SYSTEM_LOGIN_FAILED(3003, "登录显控服务获取令牌失败"),
    SYSTEM_KEEPALIVE_FAILED(3004, "token令牌保活失败"),
    SYSTEM_VERSION_FAILED(3005, "获取版本号失败"),
    SYSTEM_LOGOUT_FAILED(3006, "退出显控服务失败"),

    TV_WALL_FAILED(3010, "大屏操作失败"),
    TV_WALL_LIST_FAILED(3011, "获取所有大屏配置失败"),
    TV_WALL_LAYOUT_FAILED(3012, "获取大屏布局失败"),
    TV_WALL_QUERY_PIPELINE_FAILED(3013, "查询虚拟屏失败"),
    TV_WALL_CONFIG_FAILED(3014, "配置虚拟屏失败"),
    TV_WALL_PIPELINE_BIND_FAILED(3015, "配置虚拟屏窗口与资源的绑定关系失败"),
    TV_WALL_DELETE_FAILED(3016, "大屏删除失败"),

    TV_PLAY_FAILED(3020, "窗口操作失败"),
    TV_PLAY_START_FAILED(3021, "窗口显示失败"),
    TV_PLAY_STOP_FAILED(3022, "关闭窗口显示失败"),
    TV_PLAY_CLEAR_FAILED(3023, "清空窗口显示失败"),
    TV_PLAY_STYLE_FAILED(3024, "设置窗口风格失败"),
    TV_PLAY_OPEN_FAILED(3025, "任意开窗失败"),
    TV_PLAY_ORDER_FAILED(3026, "窗口排序失败"),
    TV_PLAY_ACTION_FAILED(3027, "窗口操作失败"),

    DECODER_OSD_FAILED(3030, "解码通道操作失败"),
    DECODER_OSD_CONFIG_FAILED(3031, "配置解码通道字幕信息失败"),
    DECODER_OSD_DELETE_FAILED(3032, ""),
    DECODER_STYLE_QUERY_FAILED(3033, ""),
    DECODER_STYLE_CONFIG_FAILED(3034, ""),

    SCHEME_FAILED(3040, "预案布局操作失败"),
    SCHEME_CONFIG_FAILED(3041, "预案的画面布局配置失败"),
    SCHEME_QUERY_FAILED(3042, "查询预案布局失败");


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
