package com.kedacom.device.core.constant;

/**
 * @auther: hxj
 * @date: 2021/5/10 15:09
 */
public enum DeviceErrorEnum {

    PARAM_ERROR(1001, ""),

    URL_ERROR(1002, "调用会议平台的URL为空"),

    UMS_SERVICE_ERROR(2000, "统一设备服务失败"),

    DEVICE_SYNCHRONIZATION_FAILED(2010, "统一设备同步失败"),
    DEVICE_NOTIFY_FAILED(2011, "设备同步失败"),
    DEVICE_GROUP_NOTIFY_FAILED(2012, "设备分组同步失败"),

    STREAM_MEDIA_FAILED(2020, "流媒体服务失败"),
    START_REC_FAILED(2021, "开启录像失败"),
    STOP_REC_FAILED(2021, "停止录像失败"),
    QUERY_REC__FAILED(2021, "查询录像失败"),
    START_AUDIO_MIX_FAILED(2022, "开启音频混音失败"),
    STOP_AUDIO_MIX_FAILED(2022, "停止音频混音失败"),
    UPDATE_AUDIO_MIX_FAILED(2022, "更新音频混音失败"),
    QUERY_ALL_AUDIO_MIX_FAILED(2022, "查询所有混音失败"),
    QUERY_AUDIO_MIX_FAILED(2022, "查询混音信息失败"),
    START_VIDEO_MIX_FAILED(2023, "开始画面合成失败"),
    STOP_VIDEO_MIX_FAILED(2023, "停止画面合成失败"),
    UPDATE_VIDEO_MIX_FAILED(2023, "更新画面合成失败"),
    QUERY_ALL_VIDEO_MIX_FAILED(2023, "查询所有画面合成失败"),
    QUERY_VIDEO_MIX_FAILED(2023, "查询画面信息失败"),
    SEND_TRANS_DATA_FAILED(2024, "发送透明通道失败"),
    QUERY_REAL_URL_FAILED(2025, "查询实时资源URL失败"),
    QUERY_HISTORY_URL_FAILED(2025, "查询历史资源URL失败"),


    MSP_REMOTE_ERROR(3001, "调用拼控服务远程接口错误"),

    SYSTEM_AUTH_FAILED(3010, "鉴权操作失败"),
    AUTH_ACCOUNT_PASSWORD_FAILED(3010, "请配置拼控服务账号密码"),
    SYSTEM_LOGIN_FAILED(3011, "登录显控服务获取令牌失败"),
    SYSTEM_KEEPALIVE_FAILED(3012, "token令牌保活失败"),
    SYSTEM_VERSION_FAILED(3013, "获取版本号失败"),
    SYSTEM_LOGOUT_FAILED(3014, "退出显控服务失败"),

    TV_WALL_FAILED(3020, "大屏操作失败"),
    TV_WALL_LIST_FAILED(3021, "获取所有大屏配置失败"),
    TV_WALL_LAYOUT_FAILED(3022, "获取大屏布局失败"),
    TV_WALL_QUERY_PIPELINE_FAILED(3023, "查询虚拟屏失败"),
    TV_WALL_CONFIG_FAILED(3024, "配置虚拟屏失败"),
    TV_WALL_PIPELINE_BIND_FAILED(3025, "配置虚拟屏窗口与资源的绑定关系失败"),
    TV_WALL_DELETE_FAILED(3026, "大屏删除失败"),

    SCHEME_FAILED(3030, "预案布局操作失败"),
    SCHEME_CONFIG_FAILED(3031, "预案的画面布局配置失败"),
    SCHEME_QUERY_FAILED(3032, "查询预案布局失败"),

    TV_PLAY_FAILED(3040, "窗口操作失败"),
    TV_PLAY_START_FAILED(3041, "窗口显示失败"),
    TV_PLAY_STOP_FAILED(3042, "关闭窗口显示失败"),
    TV_PLAY_CLEAR_FAILED(3043, "清空窗口显示失败"),
    TV_PLAY_STYLE_FAILED(3044, "设置窗口风格失败"),
    TV_PLAY_OPEN_FAILED(3045, "任意开窗失败"),
    TV_PLAY_ORDER_FAILED(3046, "窗口排序失败"),
    TV_PLAY_ACTION_FAILED(3047, "窗口操作失败"),

    DECODER_OSD_FAILED(3090, "解码通道操作失败"),
    DECODER_OSD_CONFIG_FAILED(3091, "配置解码通道字幕信息失败"),
    DECODER_OSD_DELETE_FAILED(3092, ""),
    DECODER_STYLE_QUERY_FAILED(3093, ""),
    DECODER_STYLE_CONFIG_FAILED(3094, ""),

    MCU_FAILED(4000, "会议平台不存在，请先创建会议平台"),
    MCU_OPERATE_FAILED(4001, "会议平台操作失败"),
    MCU_SSID_FAILED(4002, "会议平台ssid为空,请先登录会议平台"),

    SCHEDULE_ADD_DEVICE_FAILED(9001, "添加调度组成员设备失败"),
    SCHEDULE_DELETE_DEVICE_FAILED(9002, "删除调度组成员设备失败"),
    SCHEDULE_CREATE_FAILED(9003, "创建调度组失败"),
    SCHEDULE_DISCUSSION_DEVICE_FAILED(9004, "查询讨论组成员为空"),
    MEETING_SERVICE_NOT_ONLINE(9005, "会议服务不在线"),
    SUBSCRIBE_SCHEDULE_INFO_FAILED(9006, "订阅调度组信息失败"),
    SCHEDULE_SET_BROADCAST_FAILED(9007, "设置调度组广播源失败"),
    SCHEDULE_SET_MEDIA_FAILED(9008, "设置调度组媒体源失败"),
    SCHEDULE_PTZ_CONTROLLER(9009, "调度组PTZ控制失败");


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
