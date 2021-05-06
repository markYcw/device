package com.kedacom.device.entity.avIntegration;

/**
 * 拼控服务URI信息
 *
 * @author van.shu
 * @create 2021/3/29 14:51
 */
public enum UrlInfo {

    /*-------------------------------------------鉴权↓----------------------------------------------------------------*/

    /**
     * 登录
     */
    LOGIN("/api/v1/manage/system/login", "POST", "登录显控统一服务，获取令牌"),

    /**
     * 保活
     */
    KEEPALIVE("/api/v1/manage/system/keepalive", "POST", "成功登陆后，用户有效期为30m，需手动调用该接口保持心跳"),

    /**
     * 版本
     */
    VERSION("/api/v1/manage/system/version", "POST", "显控统一服务API版本号"),

    /**
     * 登出
     */
    LOGIN_OUT("/api/v1/manage/system/logout", "POST", "退出显控统一服务，关闭令牌"),


    /*-------------------------------------------大屏管理↓-------------------------------------------------------------*/

    /**
     * 获取大屏列表
     */
    TV_WALL_LIST("/api/v1/manage/tvwall/ls", "POST", "获取所有大屏配置"),

    /**
     * 获取大屏布局
     */
    TV_WALL_LAYOUT("/api/v1/manage/tvwall/layout", "POST", "获取大屏布局，不等分模式下使用"),

    /**
     * 查询大屏通道绑定（虚拟屏）
     */
    TV_WALL_QUERY("/api/v1/manage/tvwall/query", "POST", "主要是查询虚拟屏窗口绑定关系信息和解码通道使用模式（使用显控平台的内置解码通道资源或外部绑定的解码通道资源）"),

    /**
     * 配置大屏通道绑定（虚拟屏）
     */
    TV_WALL_CONFIG_BIND("/api/v1/manage/tvwall/config/bind", "POST", "配置虚拟屏窗口与资源的绑定关系、解码通道资源使用模式"),

    /**
     * 设置大屏（虚拟屏）
     */
    TV_WALL_CONFIG("/api/v1/manage/tvwall/config", "POST", "配置虚拟屏"),

    /**
     * 删除大屏（虚拟屏）
     */
    TV_WALL_DELETE("/api/v1/manage/tvwall/delete", "POST", "拼接屏模式不允许删除，虚拟屏模式全部删除，混合屏模式删除绑定关系"),

    /**
     * 查询坐席列表
     */
    KVL_LS("/api/v1/manage/kvm/ls", "POST", "查询所有可见的坐席，当前只有科达显控平台支持坐席查询"),


    /*-------------------------------------------预案布局↓-------------------------------------------------------------*/

    /**
     * 设置预案布局
     */
    SCHEME_CONFIG("/api/v1/manage/scheme/config", "POST", "预案的画面布局配置"),

    /**
     * 查询预案布局
     */
    SCHEME_QUERY("/api/v1/manage/scheme/query", "POST", "查询预案布局，窗口位置信息"),


    /*-------------------------------------------浏览↓--------------------------------------------------------------*/








    /*-------------------------------------------订阅↓--------------------------------------------------------------*/





    /*-----------------------------预加载任务管理(与新媒体平台保持一致)↓---------------------------------------------------*/





    /*-----------------------------------拼控资源管理↓---------------------------------------------------------------*/




    /*-----------------------------------------矩阵↓---------------------------------------------------------------*/





    /*-----------------------------------解码通道管理↓---------------------------------------------------------------*/


    /**
     * 配置解码通道字幕信息
     */
    OSD_CONFIG("/api/v1/manage/decoder/osd/config", "POST", "设置解码器的OSD"),

    /**
     * 删除解码通道字幕信息
     */
    OSD_DELETE("/api/v1/manage/decoder/osd/delete", "POST", "取消解码通道的字幕显示"),

    /**
     * 获取解码通道风格信息
     */
    DECODER_STYLE_QUERY("/api/v1/manage/decoder/style/query", "POST", "查询解码通道的画面风格及最大解码能力"),

    /**
     * 设置解码通道风格信息
     */
    DECODER_STYLE_CONFIG("/api/v1/manage/decoder/style/config", "POST", "设置解码通道的画面风格"),

    ;


    /**
     * 请求URI
     */
    private String uri;

    /**
     * 请求方式 POST GET DELETE PUT ...
     */
    private String method;

    /**
     * 说明
     */
    private String desc;


    UrlInfo(String uri, String method, String desc) {
        this.uri = uri;
        this.method = method;
        this.desc = desc;
    }
}
