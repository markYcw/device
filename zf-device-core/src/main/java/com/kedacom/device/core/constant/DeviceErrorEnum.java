package com.kedacom.device.core.constant;

/**
 * @auther: hxj
 * @date: 2021/5/10 15:09
 */
public enum DeviceErrorEnum {

    PARAM_ERROR(1001, ""),

    URL_ERROR(1002, "根据devType获取的URL为空，请核对传入参数是否合理，异常信息来自DeviceErrorEnum"),

    //全局统一错误码 addBy ycw
    DEVICE_NOT_FOUND(1003,"设备未找到请检查"),
    DEVICE_NOT_LOGIN(1004,"设备未登录，请先登录设备"),
    DEVICE_HEART_BEAT_FAILED(1005,"发送心跳失败"),
    IP_OR_NAME_REPEAT(1006,"IP或名称重复"),

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
    GET_AUDIO_CAP_FAILED(2026, "获取音频能力集失败"),
    CTRL_AUDIO_ACT_FAILED(2027, "控制音频功率上报失败"),
    SET_AUDIO_ACT_INTERVAL_FAILED(2028, "设置音频功率上报间隔失败"),
    GET_BURN_STATE_FAILED(2029, "刻录状态请求失败"),
    GET_SVR_AUDIO_ACT_STATE_FAILED(2030, "获取当前语音激励状态失败"),


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

    //MCU相关错误码 addBy ycw
    MCU_LOGIN_FAILED(4000, "登录会议平台失败"),
    MCU_LOGOUT_FAILED(4001, "登出会议平台失败"),
    MCU_ACCOUNT_FAILED(4002, "创建/删除账户失败"),
    MCU_CONF_S_FAILED(4003, "获取会议列表失败"),
    MCU_TEMPLATES_FAILED(4004, "获取会议模板列表失败"),
    MCU_CONF_INFO_FAILED(4005, "获取会议信息失败"),
    MCU_CONF_FAILED(4006, "创建/删除会议失败"),
    MCU_CONF_TEMPLATE_FAILED(4007, "开启会议模板失败"),
    MCU_MT_MEMBERS_FAILED(4008, "获取与会成员失败"),
    MCU_MT_FAILED(4009, "添加/删除终端失败"),
    MCU_MT_CALL_FAILED(4010, "呼叫/挂断终端失败"),
    MCU_SPEAKER_FAILED(4011, "设置/取消发言人失败"),
    MCU_CHAIRMAN_FAILED(4012, "设置/取消主席失败"),
    MCU_SILENCE_FAILED(4013, "静音失败"),
    MCU_MUTE_FAILED(4014, "哑音失败"),
    MCU_VOLUME_FAILED(4015, "调节音量失败"),
    MCU_DUAL_FAILED(4016, "终端双流控制失败"),
    MCU_VIDEO_MIX_FAILED(4017, "开始/停止画面合成失败"),
    MCU_AUDIO_MIX_FAILED(4018, "开始/停止混音失败"),
    MCU_AUDIO_MIX_MEMBER_FAILED(4019, "添加/删除混音成员失败"),
    MCU_REC_FAILED(4020, "开始/暂停/恢复/停止录像失败"),
    MCU_TV_WALLS_FAILED(4021, "获取电视墙列表失败"),
    MCU_TV_WALL_FAILED(4022, "开始/停止上电视墙失败"),
    MCU_EXCHANGE_FAILED(4023, "开始/停止码流交换失败"),
    MCU_MESSAGE_FAILED(4024, "发送短消息失败"),
    MCU_HB_FAILED(4025, "发送心跳失败"),
    MCU_CONF_TEMPLATE_S_FAILED(4026, "mcu创建/删除会议模板失败"),
    MCU_REC_STATUS_FAILED(4027, "mcu获取录像状态失败"),
    MCU_DEPARTMENTS_FAILED(4028, "mcu查询所有部门失败"),

    //SVR相关错误码 addBy ycw
    SVR_LOGIN_FAILED(5001, "SVR登录失败"),
    SVR_LOGOUT_FAILED(5002, "SVR登出失败"),
    SVR_CAP_FAILED(5003, "获取SVR能力集失败"),
    SVR_TIME_FAILED(5004, "获取SVR时间失败"),
    SVR_SEARCH_DEV_FAILED(5005, "搜索编解码设备失败"),
    SVR_EN_CHN_LIST_FAILED(5006, "获取编码通道列表失败"),
    SVR_EN_CHN_FAILED(5007, "添加/删除编码通道失败"),
    SVR_EN_CP_RESET_FAILED(5008, "获取编码器的预置位失败"),
    SVR_CP_RESET_FAILED(5009, "修改编码器的预置位失败"),
    SVR_DE_CHN_LIST_FAILED(5010, "获取解码通道列表失败"),
    SVR_DE_CHN_FAILED(5011, "添加/删除解码通道失败"),
    SVR_DEC_PARAM_FAILED(5012, "获取解码参数失败"),
    SVR_EN_DEC_PARAM_FAILED(5013, "设置解码参数失败"),
    SVR_PTZ_FAILED(5014, "PTZ控制失败"),
    SVR_REMOTE_FAILED(5015, "启用/停止远程点失败"),
    SVR_REMOTE_CFG_FAILED(5016, "获取远程点配置失败"),
    SVR_REMOTE_PUT_CFG_FAILED(5017, "修改远程点配置失败"),
    SVR_DUAL_FAILED(5018, "发送双流失败"),
    SVR_BURN_FAILED(5019, "刻录控制失败"),
    SVR_RE_BURN_FAILED(5020, "补刻失败"),
    SVR_APPEND_BURN_FAILED(5021, "追加刻录任务失败"),
    SVR_CREATE_BURN_FAILED(5022, "新建刻录任务失败"),
    SVR_BURN_TASK_LIST_FAILED(5023, "获取刻录任务失败"),
    SVR_DVD_DOOR_FAILED(5024, "DVD仓门控制失败"),
    SVR_REC_LIST_FAILED(5025, "查询录像失败"),
    SVR_GET_MERGE_FAILED(5026, "获取画面合成失败"),
    SVR_MERGE_FAILED(5027, "设置画面合成失败"),
    SVR_GET_OSD_FAILED(5028, "获取画面叠加失败"),
    SVR_OSD_FAILED(5029, "设置画面叠加失败"),
    SVR_AUDIO_FAILED(5030, "语音激励控制失败"),

    //cu相关错误码
    CU_LOGIN_FAILED(6001, "CU登录失败"),
    CU_LOGOUT_FAILED(6002, "CU登出失败"),
    CU_LOCAL_DOMAIN_FAILED(6003, "获取平台域信息失败"),
    CU_DOMAINS_FAILED(6004, "获取域链表失败"),
    CU_TIME_FAILED(6005, "获取平台时间失败"),
    CU_VIEW_TREES_FAILED(6006, "获取多视图设备树失败"),
    CU_SELECT_TREE_FAILED(6007, "选择当前操作的设备树失败"),
    CU_DEV_GROUPS_FAILED(6008, "获取设备组信息失败"),
    CU_DEVICES_FAILED(6009, "获取设备信息失败"),
    CU_CONTROL_PTZ_FAILED(6010, "PTZ控制操作失败"),
    CU_START_REC_FAILED(6011, "开启平台录像操作失败"),
    CU_STOP_REC_FAILED(6012, "关闭平台录像操作失败"),
    CU_START_PU_REC_FAILED(6013, "开启前端录像操作失败"),
    CU_STOP_PU_REC_FAILED(6014, "关闭前端录像操作失败"),
    CU_OPEN_LOCKING_REC_FAILED(6015, "打开录像锁定操作失败"),
    CU_CANCEL_LOCKING_REC_FAILED(6016, "取消录像锁定操作失败"),
    CU_QUERY_DISK_FAILED(6017, "查询磁阵(磁盘)信息失败"),
    CU_QUERY_VIDEO_DAYS_FAILED(6018, "查询录像日历信息失败"),
    CU_QUERY_VIDEO_FAILED(6019, "查询录像失败"),
    QUERY_MONITOR_ERROR(6020,"根据数据库ID查询监控平台树失败"),
    GET_CU_DEVICE_INFO_ERROR(6021,"获取设备详细信息失败"),
    GET_CU_CHANNEL_INFO_ERROR(6022,"获取设备具体通道信息失败"),
    GET_CU_GROUP_ERROR(6023,"获取监控平台（分组/设备）信息失败，设备未加载完成请稍后重试"),
    GET_CU_CHANNEL_LIST_ERROR(6024,"获取设备通道集合信息失败"),
    CU_GB_ID_ERROR(6025,"获取国标id失败"),
    CU_Pu_ID_TWO_ERROR(6026,"获取平台2.0puId失败"),
    CU_SUBSCRIBE_ERROR(6027,"单个设备状态订阅失败"),

    // 终端相关错误码
    MT_LOGIN_FAILED(7000, "终端登录失败"),
    MT_LOGOUT_FAILED(7001, "终端退出登录失败"),
    MT_HEART_BEAT_FAILED(7002, "发送心跳失败"),
    MT_TYPES_FAILED(7003, "获取终端类型失败"),
    MT_START_P2P_FAILED(7004, "开启点对点会议失败"),
    MT_STOP_P2P_FAILED(7005, "停止点对点会议失败"),
    MT_SET_MUTE_FAILED(7006, "设置静音失败"),
    MT_SET_DUMB_FAILED(7007, "设置哑音失败"),
    MT_GET_MT_STATUS_FAILED(7008, "获取终端状态失败"),
    MT_GET_MUTE_FAILED(7009, "获取静音失败"),
    MT_GET_DUMB_FAILED(7010, "获取哑音失败"),
    MT_SET_VOLUME_FAILED(7011, "设置音量失败"),
    MT_GET_VOLUME_FAILED(7012, "音量获取失败"),
    MT_KEYFRAME_FAILED(7013, "请求关键帧失败"),
    MT_START_DUAL_FAILED(7014, "双流控制失败"),
    MT_PTZ_CONTROLLER_FAILED(7015, "ptz控制失败"),
    MT_SET_PIP_MODE_FAILED(7016, "设置画面显示模式失败"),

    // 录像机相关错误码
    VS_LOGIN_FAILED(8000,"登录VRS失败请稍后重试"),
    VS_QUERY_REC_FAILED(8001, "查询录像失败"),
    VS_QUERY_LIVE_FAILED(8002, "查询直播失败"),

    SCHEDULE_ADD_DEVICE_FAILED(9001, "添加调度组成员设备失败"),
    SCHEDULE_DELETE_DEVICE_FAILED(9002, "删除调度组成员设备失败"),
    SCHEDULE_CREATE_FAILED(9003, "创建调度组失败"),
    SCHEDULE_DISCUSSION_DEVICE_FAILED(9004, "查询讨论组成员为空"),
    MEETING_SERVICE_NOT_ONLINE(9005, "会议服务不在线"),
    SUBSCRIBE_SCHEDULE_INFO_FAILED(9006, "订阅调度组信息失败"),
    SCHEDULE_SET_BROADCAST_FAILED(9007, "设置调度组广播源失败"),
    SCHEDULE_SET_MEDIA_FAILED(9008, "设置调度组媒体源失败"),
    SCHEDULE_PTZ_CONTROLLER(9009, "调度组PTZ控制失败"),


    ;

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
