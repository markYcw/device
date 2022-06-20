package com.kedacom.power.model;

/**
 * @ClassName: KmResultCodeEnum
 * @Description: KM服务异常状态码
 * @author: ycw
 * @date: 2020/12/23  下午 1:38
 * @Version: 1.0
 */
public enum KmResultCodeEnum {

    /**
     * 全局错误码
     */
    SUCCESS(1, "成功"),
    ERROR(0, "失败"),
    SERVICE_ERROR(2, "服务器异常"),
    DEVICE_NOT_FOUND_ERROR(999, "未找到该设备请检查"),


    /**
     * SVR设备相关错误码
     */
    ERROR_OF_NONE_SVR(1000, "未查询到相应的设备"),
    TESTSVRERROR(1001, "SVR登陆失败请稍后重试"),
    STARTBURNERROR(1002, "开启刻录失败请稍后重试"),
    STOPBURNERROR(1003, "停止刻录失败请稍后重试"),
    GETSVRSTATEERROR(1004, "获取SVR状态失败请稍后重试"),
    SETOSDERROR(1005, "设置叠加案件失败请稍后重试"),
    CONTROLDVDDOORERROR(1006, "控制DVD仓门失败请稍后重试"),
    DELETESVRERROR(1007, "删除SVR失败请稍后重试"),
    DOWNLOADREC(1008, "下周录像失败请稍后重试"),
    LOGOUTSVRERROR(1009, "登出SVR失败请稍后重试"),
    ERROR_OF_CREATE_BURN(1010, "新建刻录失败"),
    GET_REM_CHN_LIST_ERROR(1011,"获取远程点通道列表失败"),
    GET_ENC_DEV_CAP_ERROR(1012,"获取编码器能力集失败"),
    NEW_EXTRA_BURN_ERROR(1013,"额外刻录失败"),
    APPEND_BURN_ERROR(1014,"追加刻录失败,请稍后重试"),

    /**
     * 监控平台操作相关错误码
     */
    QUERY_MONITOR_ERROR(2001, "查询监控平台树状图信息失败请稍后重试"),
    QUERY_CU_INFO_ERROR(2002, "查询监控平台信息失败请稍后重试"),
    START_DEV_REC_ERROR(2003, "开启平台录像失败请稍后重试"),
    STOP_DEV_REC_ERROR(2004, "关闭平台录像失败请稍后重试"),
    START_PU_REC_ERROR(2005, "开启前端录像失败请稍后重试"),
    STOP_PU_REC_ERROR(2006, "关闭前端录像失败请稍后重试"),
    GET_CU_DEVICE_INFO_ERROR(2007, "获取设备详细信息失败请稍后重试"),
    GET_CU_CHANNEL_INFO_ERROR(2008, "获取设备通道具体信息失败请稍后重试"),
    DELETE_DEV_ERROR(2009, "删除监控平台失败请稍后重试"),
    GET_TIME_ERROR(2010, "获取监控平台时间失败请稍后重试"),
    GET_CU_TIME_ERROR(2011, "获取同步监控平台时间失败请稍后重试"),
    GET_TV_WALL_ERROR(2012, "获取电视墙失败请稍后重试"),
    START_LOOK_TV_WALL_ERROR(2013, "开始选看电视墙失败请稍后重试"),
    STOP_LOOK_TV_WALL_ERROR(2014, "开始选看电视墙失败请稍后重试"),
    PUT_TV_WALL_ERROR(2015, "添加/修改电视墙失败请稍后重试"),
    DEL_TV_WALL_ERROR(2016, "删除电视墙失败请稍后重试"),
    LOAD_TV_WALL_SCHEME_ERROR(2017, "载入电视墙预案失败请稍后重试"),
    PUT_TV_WALL_SCHEME_ERROR(2018, "添加/修改电视墙预案失败请稍后重试"),
    DEL_TV_WALL_SCHEME_ERROR(2019, "删除电视墙预案失败请稍后重试"),
    GET_TV_WALL_SCHEME_ERROR(2020, "获取电视墙预案失败请稍后重试"),
    QUERY_REC_TIME_ERROR(2021, "录像时段查询失败请稍后重试"),
    REC_CONNECT_CU_ERROR(2022, "重连监控平台失败请稍后重试"),
    REC_DAYS_ERROR(2023, "录像日历查询失败请稍后重试"),
    GET_KDMNO_ERROR(2024, "获取监控平台1.0设备编号失败"),

    /**
     * 发布直播点播相关错误码
     */
    VOD_STREAM_ERROR(3001, "发布点播失败没有找到符合调节的录像请检查"),
    LIVE_STREAM_ERROR(3002, "发布直播失败"),
    STREAM_ERROR(3003, "发布rtsp源失败"),
    VOD_DEVICE_OFFLINE_ERROR(3004, "发布点播失败设备不在线请检查"),


    /**
     * 读取key相关错误码
     */
    GET_KEY_ERROR(4001, "获取本机key失败请稍后重试"),
    READ_MACHE_CODE_ERROR(4002, "获取许可证失败请稍后重试"),
    UPDATE_KEYS_ERROR(4003, "上传key失败请稍后重试"),

    /**
     * MCU相关错误码
     */
    GET_CONF_TEM_ERROR(5001, "获取会议/模板列表失败"),
    GET_CONF_LIST_ERROR(5002, "获取视频会议列表失败"),
    GET_LOCAL_MT_LIST_ERROR(5003, "获取本地会议终端列表失败"),
    CREATE_CONF_ERROR(5004, "创建会议失败"),
    GET_MCU_STATUS_ERROR(5005, "获取会议平台链接状态失败"),
    START_CON_TEM_ERROR(5006, "开启模板会议(个人模板)失败"),
    RELEASE_CONF_ERROR(5007, "结束会议失败"),
    ADD_MT_ERROR(5008, "添加终端失败"),
    DEL_MT_ERROR(5009, "删除终端失败"),
    CALL_MT_ERROR(5010, "呼叫终端失败"),
    GET_INSPECTION_ERROR(5011, "获取终端选看列表失败"),
    SET_MT_VOLUME_ERROR(5012, "调节音量失败"),
    START_PART_MIX_ERROR(5013, "定制混音失败"),
    STOP_MIX_ERROR(5014, "停止混音失败"),
    SEND_MSG_ERROR(5015, "发送短消息失败"),
    SET_SPEAKER_ERROR(5016, "设置发言人失败"),
    CANCEL_SPEAKER_ERROR(5017, "取消发言人失败"),
    GET_SPEAKER_ERROR(5018, "获取发言人失败"),
    SET_CHAIR_MAN_ERROR(5019, "设置主席失败"),
    CANCEL_CHAIR_MAN_ERROR(5020, "取消主席失败"),
    GET_CHAIR_MAN_ERROR(5021, "获取主席失败"),
    ALL_MT_QUIET_ERROR(5022, "全场终端静音/哑音设置失败"),
    MT_QUIET_ERROR(5023, "单个终端静音/哑音设置失败"),
    START_STREAM_CHANGE_ERROR(5024, "建立码流交换失败"),
    STOP_STREAM_CHANGE_ERROR(5025, "拆除码流交换失败"),
    START_VMP_ERROR(5026, "开始画面合成失败"),
    STOP_VMP_ERROR(5027, "停止画面合成失败"),
    GET_VCR_RECORD_STATUS_BY_CONF_ERROR(5028, "根据会议ID获取录像录制状态失败"),
    GET_VCR_RECORD_STATUS_BY_REC_ID_ERROR(5029, "根据录像ID获取录像录制状态失败"),
    START_REC_ERROR(5030, "开始录像失败"),
    STOP_REC_ERROR(5031, "停止录像失败"),
    GET_MCU_TV_WALL_ERROR(5032, "获取电视墙失败"),
    START_LOOK_MCU_TV_WALL_ERROR(5033, "终端开始上墙失败"),
    STOP_LOOK_MCU_TV_WALL_ERROR(5034, "停止终端上墙失败"),
    DELAY_CONF_ERROR(5035, "延长会议时间失败"),
    GET_CONF_INFO_ERROR(5036, "获取会议信息失败"),
    GET_JOINED_MT_ERROR(5037, "获取与会成员失败"),
    GET_MT_STATUS_ERROR(5038, "获取终端状态失败"),
    DROP_MT_ERROR(5039, "挂断终端失败"),
    MT_DUAL_STREAM_CTRL_ERROR(5040, "终端双流发送控制失败"),
    START_TEN_TIRE_MIX_ERROR(5041, "智能混音失败"),
    ADD_MIX_MEMBER_ERROR(5042, "添加混音成员失败"),
    DEL_MIX_MEMBER_ERROR(5043, "删除混音成员失败"),
    GET_VCR_STATUS_ERROR(5044, "获取录像机状态列表失败"),
    PAUSE_REC_ERROR(5045, "暂停录像失败"),
    RESUME_REC_ERROR(5046, "恢复录像失败"),
    START_BR_ST_ERROR(5047, "开始广播失败"),
    STOP_BR_ST_ERROR(5048, "停止广播失败"),
    STOP_MSG_ERROR(5049, "停止短消息失败"),
    GET_TERMINALS_ERROR(5050, "获取5.0终端列表失败"),
    GET_OLD_TERMINALS_ERROR(5051, "获取三代高清终端列表(非受管)失败"),

    /**
     * VRS2000B相关错误码
     */
    LOGIN_VRS_ERROR(6001, "登录VRS失败请检查"),
    LOGOUT_VRS_ERROR(6002, "登出VRS失败请检查"),
    GET_ROOM_ERROR(6003, "申请录像室失败"),
    ROOM_CTRL_ERROR(6004, "录像室控制失败"),
    PATCH_BURN_ERROR(6005, "补刻失败"),
    REC_PLAY_ERROR(6006, "播放录像失败"),
    QUERY_REC_TASK_ERROR(6007, "查询录像任务失败"),
    BURN_RECORD_ERROR(6008, "刻录附件失败"),
    GET_ROOM_STATUS_ERROR(6009, "录像室状态获取失败"),
    REC_PLAY_VCR_ERROR(6010, "播放录像控制失败"),
    GET_SYS_STATUS_ERROR(6011, "系统状态获取失败"),
    GATE_WAY_CFG_ERROR(6012, "中间件配置失败"),
    MERGE_CFG_ERROR(6013, "刻录合成通道配置失败"),
    DVD_LOCK_CFG_ERROR(6014, "DVD封盘配置失败"),
    QUERY_REC_FILE_ERROR(6015, "录像下载失败"),

    /**
     * VRS2100/4100相关错误吗（录播服务器5.0/5.1）
     */
    QUERY_REC_LIST_ERROR(6015, "分页查询录像失败"),
    QUERY_HTTP_REC_LIST_ERROR(6016, "分页查询HTTP录像失败"),
    QUERY_LIVE_REC_LIST_ERROR(6017, "分页查询直播录像失败"),

    /**
     * 终端相关错误码
     */
    GET_MT_TYPE_ERROR(7001, "获取终端类型失败"),
    START_P2P_ERROR(7002, "开启点对点会议失败"),
    STOP_P2P_ERROR(7003, "停止点对点会议失败"),
    CHECK_MT_IN_CONF_ERROR(7004, "检查终端是否在会议中失败,请检查"),
    SET_DUMB_MUTE_ERROR(7005, "静音哑音设置失败,请检查"),
    GET_DUMB_MUTE_ERROR(7006, "静音哑音状态获取失败,请检查"),
    GET_VIDEO_SRC_ERROR(7007, "获取终端视频源失败"),
    SELECT_VIDEO_SRC_ERROR(7008, "选择视频源失败"),
    GET_CUR_VIDEO_SRC_ERROR(7009, "获取当前视频源失败"),
    MT_START_DUAL_ERROR(7010, "双流发送控制失败"),
    PTZ_CTRL_ERROR(7011, "PTZ控制失败"),
    SET_PIP_MODE_ERROR(7012, "设置画面显示模式失败"),
    PIP_SWITCH_ERROR(7013, "画面切换失败"),
    VOLUME_CTRL_ERROR(7014, "音量控制失败"),
    MT_LOGIN_ERROR(7015, "获取mtId失败，请检查此会议终端是否成功注册"),
    GET_KEY_FRAME_ERROR(7016, "请求关键帧失败"),

    /**
     * 电源设备相关错误码
     */
    ERROR_OF_PARAMS_ERROR(8000, "请求参数不合法"),
    ERROR_OF_PORT_REGEX(8001, "端口格式不合法"),
    ERROR_OF_DATA_REPEAT(8002, "电源配置端口已存在"),
    ERROR_OF_DATA_NONE(8003, "电源设备不存在"),
    ERROR_OF_FEIGN_UNKNOWN(8004, "服务异常调用，请稍后重试"),
    ERROR_OF_UNKNOWN_DATA(8005, "不能识别的端口，请先配置端口"),
    ERROR_OF_START_POWER_TCP(8006, "启动tcp端口失败"),
    ERROR_OF_DEVICE_ALREADY_INUSE(8007, "电源设备已存在"),
    ERROR_OF_DEVICE_MAC_NOT_FOUND(8008, "电源设备mac地址未找到，或电源设备已离线"),
    ERROR_OF_PORT_NOT_NULL(8009, "端口号不能为空"),
    ERROR_OF_DO_NOT_ALLOW_DEV_TYPE(8010, "不支持的设备类型"),
    ERROR_OF_DIFFERENT_DEV_TYPE(8011, "设备类型不一致，无法修改"),
    ERROR_OF_NONE_FOUND_DEVICE(8012, "未查询到相应的设备信息"),
    ERROR_OF_NONE_RK100_DEVICE_SN(8013, "RK100设备序列号不能为空"),
    ERROR_OF_RK100_IP_NOT_NULL(8014, "RK100设备ip已存在"),
    ERROR_OF_RK100_NAME_NOT_NULL(8015, "RK100设备名称已存在"),
    ERROR_OF_RK100_IP_AND_DEVICE_SN_SEAM(8016, "该RK100设备ip已对应相同的设备序列号"),
    ERROR_OF_RK100_DEVICE_SN_NOT_NULL(8017, "RK100设备序列号已存在"),
    ERROR_OF_RK100_NOT_LOGIN_IN(8018, "RK100设备未登录"),
    ERROR_OF_RK100_ALREADY_LOGIN_IN(8019, "RK100设备已登录，请先退出"),

    /**
     * 签名板相关错误码
     */
    SYN_RECORD_ERROR(9001, "笔录同步失败"),
    SIGNATURE_ERROR(9002, "笔录签名失败"),
    LOGIN_E10PRO_ERROR(9003, "登录EPro失败"),
    POST_PDF_ERROR(9004, "下发PDF失败"),
    GET_FINGER_PRINT(9005, "手动获取指纹图片失败"),
    OPEN_CAMERA(50010, "打开设备相机失败"),
    CLOSE_CAMERA(9007, "关闭设备相机失败"),
    E_PRO_CAPTURE(9008, "抓拍图片失败"),
    E_PRO_START_REC(9009, "启动录像失败"),
    E_PRO_STOP_REC(9010, "停止录像失败"),
    E_PRO_GET_REC(9011, "获取录像失败"),

    /**
     * ip或名称校验失败相关错误码
     */
    IP_OR_NAME_REPEAT(10010, "IP或名称重复"),
    IP_CANT_MATCH_ERR_MSG(10011, "根据异常信息未匹配到错误码"),

    /**
     * 人证核验终端相关错误码
     */
    ERROR_OF_VERIFICATION_NAME_TOO_LONG(20001, "设备名称最大16个字符"),
    ERROR_OF_VERIFICATION_SAME_DEV_ID(20002, "该设备Id已存在"),
    ERROR_OF_VERIFICATION_NONE_DEVICE(20003, "不存在的人证核验终端设备"),
    ERROR_OF_VERIFICATION_SUBSCRIBE_IN(20004, "该地址已经订阅，请勿重复订阅"),
    ERROR_OF_VERIFICATION_SUBSCRIBE_OUT(20005, "该地址未订阅"),
    ERROR_OF_VERIFICATION_SAME_DEV_NAME(20006, "该设备名称已存在"),
    ERROR_OF_VERIFICATION_MSG_TYPE_ERROR(20007, "未知消息类型"),

    ;


    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    KmResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
