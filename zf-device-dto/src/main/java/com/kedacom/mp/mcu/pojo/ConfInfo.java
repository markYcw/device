package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/16 11:05
 * @description 会议信息
 */
@Data
@ApiModel(value = "会议信息")
public class ConfInfo implements Serializable {

    @ApiModelProperty(value = "会议名称")
    private String confName;

    @ApiModelProperty(value = "虚拟会议室名称，当返回为空字符串时说明不是召开的虚拟会议室")
    private String roomName;

    @ApiModelProperty(value = "会议号码,最大字符长度：48个字节")
    private String confId;

    @ApiModelProperty(value = "会议级别：1-16, 值越小, 级别越高(用于抢呼已在其他会议里的终端)")
    private Integer confLevel;

    @ApiModelProperty(value = "会议类型;0-传统;1-端口")
    private Integer confType;

    @ApiModelProperty(value = "会议开始时间")
    private String startTime;

    @ApiModelProperty(value = "会议结束时间")
    private String endTime;

    @ApiModelProperty(value = "会议时长 0为永久会议")
    private Integer duration;

    @ApiModelProperty(value = "会议码率")
    private Integer bitrate;

    @ApiModelProperty(value = "会议免打扰:0-关闭；1-开启；")
    private Integer closedConf;

    @ApiModelProperty(value = "会议安全:0-公开会议；1-隐藏会议；")
    private Integer safeConf;

    @ApiModelProperty(value = "传输加密类型:0-不加密；2-AES加密；")
    private Integer encryptedType;

    @ApiModelProperty(value = "终端双向认证:0-关闭；1-开启；")
    private Integer encryptedAuth;

    @ApiModelProperty(value = "初始化哑音:0-否；1-是；")
    private Integer mute;

    @ApiModelProperty(value = "全场哑音例外，参数为1时，若执行全场哑音操作，主席和发言人不会被哑音，若执行单个哑音操作时可以被哑音\n" +
            "0-否；\n" +
            "1-是；")
    private Integer muteFilter;

    @ApiModelProperty(value = "初始化静音:0-否；1-是；")
    private Integer silence;

    @ApiModelProperty(value = "视频质量:0-质量优先；1-速度优先；")
    private Integer videoQuality;

    @ApiModelProperty(value = "传输加密AES加密密钥 最大字符长度：16字节")
    private String encryptedKey;

    @ApiModelProperty(value = "双流权限:0-发言会场；1-任意会场；2-指定会场；")
    private Integer dualMode;

    @ApiModelProperty(value = "是否公共会议室:0-否；1-是；")
    private Integer publicConf;

    @ApiModelProperty(value = "自动结会(少于两个终端时自动结会):0-不自动结束；1-自动结束；")
    private Integer autoEnd;

    @ApiModelProperty(value = "预占资源(创会时就预占音视频适配器):0-不预占；1-预占；")
    private Integer preoccupyResource;

    @ApiModelProperty(value = "最大与会终端数:8-小型8方会议；32-32方会议；64-64方会议；192-大型192方会议；")
    private Integer maxJoinMt;

    @ApiModelProperty(value = "是否强制广播:0-不强制广播；1-强制广播；")
    private Integer forceBroadCast;

    @ApiModelProperty(value = "是否支持码流纠错:0-关闭；1-开启；")
    private Integer fecMode;

    @ApiModelProperty(value = "是否开启语音激励:0-否；1-是；")
    private Integer voiceActivityDetection;

    @ApiModelProperty(value = "语音激励敏感度(s)，最小值5s, 开启语音激励时有效")
    private Integer vacInterval;

    @ApiModelProperty(value = "呼叫次数")
    private Integer callTimes;

    @ApiModelProperty(value = "呼叫间隔(秒)")
    private Integer callInterval;

    @ApiModelProperty(value = "呼叫模式:0-手动呼叫；2-定时呼叫；")
    private Integer callMode;

    @ApiModelProperty(value = "级联模式:0-简单级联；1-合并级联；")
    private Integer cascadeMode;

    @ApiModelProperty(value = "是否级联上传:0-否；1-是；")
    private Integer cascadeUpload;

    @ApiModelProperty(value = "是否级联回传:0-否;1-是；")
    private Integer cascadeReturn;

    @ApiModelProperty(value = "级联回传带宽参数")
    private Integer cascadeReturnPara;

    @ApiModelProperty(value = "是否在合成:0-否；1-是；")
    private Integer vmpEnable;

    @ApiModelProperty(value = "是否在混音0-否；1-是；")
    private Integer mixEnable;

    @ApiModelProperty(value = "是否在轮询:0-否；1-是；")
    private Integer pollEnable;

    @ApiModelProperty(value = "是否需要密码:0-否；1-是；")
    private Integer needPassword;

    @ApiModelProperty(value = "")
    private Integer oneReforming;

    @ApiModelProperty(value = "成为发言人后立即发起内容共享0-否；1-是；")
    private Integer doubleFlow;

    @ApiModelProperty(value = "创会平台moid")
    private String platformId;

    @ApiModelProperty(value = "是否有上级级联会议室:0-否；1-是；")
    private Integer superiorCascade;

    @ApiModelProperty(value = "是否有下级级联会议室:0-否；1-是；")
    private Integer subordinateCascade;

    @ApiModelProperty(value = "视频会议唯一id")
    private String meetingId;

    @ApiModelProperty(value = "主视频格式列表")
    private List<VideoFormat> videoFormats;

    @ApiModelProperty(value = "会议发起者")
    private Creator creator;

}
