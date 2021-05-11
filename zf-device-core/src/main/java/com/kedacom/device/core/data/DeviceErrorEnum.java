package com.kedacom.device.core.data;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:09
 */
public enum DeviceErrorEnum {

    PARAM_ERROR(1001, ""),

    MSP_REMOTE_ERROR(3001, "调用远程接口错误"),
    SYSTEM_LOGIN_FAILED(3002, "登录显控服务获取令牌失败"),
    SYSTEM_KEEPALIVE_FAILED(3003, "token令牌保活失败"),
    SYSTEM_VERSION_FAILED(3004, "获取版本号失败"),
    SYSTEM_LOGOUT_FAILED(3005, "退出显控服务失败"),

    TVWALL_LIST_FAILED(3010, "获取所有大屏配置失败"),
    TVWALL_LAYOUT_FAILED(3011, "获取大屏布局失败"),
    TVWALL_QUERY_PIPELINE_FAILED(3012, "查询虚拟屏失败"),
    TVWALL_CONFIG_FAILED(3013, "配置虚拟屏失败"),
    TVWALL_PIPELINE_BIND_FAILED(3014, "配置虚拟屏窗口与资源的绑定关系失败"),
    TVWALL_DELETE_FAILED(3015, "大屏删除失败"),

    TVPLAY_START_FAILED(3020, "窗口显示失败"),
    TVPLAY_STOP_FAILED(3021, "关闭窗口显示失败"),
    TVPLAY_CLEAR_FAILED(3022, "清空窗口显示失败"),
    TVPLAY_STYLE_FAILED(3023, "设置窗口风格失败"),
    TVPLAY_OPEN_FAILED(3024, "任意开窗失败"),
    TVPLAY_ORDER_FAILED(3025, "窗口排序失败"),
    TVPLAY_ACTION_FAILED(3026, "窗口操作失败"),

    TVWALL_LIST(301023, "获取所有大屏配置失败");

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
