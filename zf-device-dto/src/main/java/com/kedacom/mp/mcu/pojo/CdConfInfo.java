package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 14:29
 * @description 创建会议时会议信息
 */
@Data
@ApiModel(description =  "创建会议时会议信息")
public class CdConfInfo implements Serializable {

    @ApiModelProperty(value = "名称")
    private String name;

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

    @ApiModelProperty(value = "会议类型:0-传统会议；1-端口会议；")
    private Integer confType;

    @ApiModelProperty(value = "呼叫模式:0-手动呼叫；2-定时呼叫；")
    private Integer callMode;

    @ApiModelProperty(value = "呼叫次数")
    private Integer callTimes;

    @ApiModelProperty(value = "呼叫间隔(秒)")
    private Integer callInterval;

    @ApiModelProperty(value = "是否开启初始化哑音:0-否；1-是；")
    private Integer mute;

    @ApiModelProperty(value = "是否开启初始化静音:0-否；1-是；")
    private Integer silence;

    @ApiModelProperty(value = "视频质量,其中租赁环境默认设为质量优先，自建环境以api下参为准\n" +
            "0-质量优先；1-速度优先；")
    private Integer videoQuality;

    @ApiModelProperty(value = "传输加密AES加密密钥 最大字符长度：16字节")
    private String encryptedKey;

    @ApiModelProperty(value = "双流权限:0-发言会场；1-任意会场；2-指定会场；")
    private Integer dualMode;

    @ApiModelProperty(value = "是否开启语音激励:0-否；1-是；")
    private Integer voiceActivityDetection;

    @ApiModelProperty(value = "级联模式:0-简单级联；1-合并级联；")
    private Integer cascadeMode;

    @ApiModelProperty(value = "是否级联上传:0-否；1-是；")
    private Integer cascadeUpload;

    @ApiModelProperty(value = "是否级联回传:0-否;1-是；")
    private Integer cascadeReturn;

    @ApiModelProperty(value = "级联回传带宽参数")
    private Integer cascadeReturnPara;

    @ApiModelProperty(value = "是否来宾会议室: 1-是；:0-否；1-是；")
    private Integer publicConf;

    @ApiModelProperty(value = "最大与会终端数:8-小型8方会议；32-32方会议；64-64方会议；192-大型192方会议；")
    private Integer maxJoinMt;

    @ApiModelProperty(value = "会议中无终端时，是否自动结会，永久会议时默认为0\n" +
            "0-否；\n" +
            "1-是；")
    private Integer autoEnd;

    @ApiModelProperty(value = "预占资源:0-否；1-是；")
    private Integer preoccpuyResource;

    @ApiModelProperty(value = "归一重整:0-不使用;1-使用；")
    private Integer oneReforming;

    @ApiModelProperty(value = "发言人")
    private Speaker speaker;

    @ApiModelProperty(value = "主席")
    private Chairman chairman;

    @ApiModelProperty(value = "混音信息")
    private Mix mix;

    @ApiModelProperty(value = "制定混音时的混音成员列表")
    private List<MixMember> members;

    @ApiModelProperty(value = "主视频格式列表")
    private List<VideoFormat> videoFormats;

    @ApiModelProperty(value = "参会成员 创建虚拟会议室时可填")
    private List<InviteMember> inviteMembers;

    @ApiModelProperty(value = "录像设置")
    private Recorder recorder;
























    @ApiModelProperty(value = "全场哑音例外，参数为1时，若执行全场哑音操作，主席和发言人不会被哑音，若执行单个哑音操作时可以被哑音\n" +
            "0-否；\n" +
            "1-是；")
    private Integer muteFilter;










    @ApiModelProperty(value = "预占资源(创会时就预占音视频适配器):0-不预占；1-预占；")
    private Integer preoccupyResource;



    @ApiModelProperty(value = "是否强制广播:0-不强制广播；1-强制广播；")
    private Integer forceBroadCast;

    @ApiModelProperty(value = "是否支持码流纠错:0-关闭；1-开启；")
    private Integer fecMode;



    @ApiModelProperty(value = "语音激励敏感度(s)，最小值5s, 开启语音激励时有效")
    private Integer vacInterval;















    @ApiModelProperty(value = "是否在合成:0-否；1-是；")
    private Integer vmpEnable;

    @ApiModelProperty(value = "是否在混音0-否；1-是；")
    private Integer mixEnable;

    @ApiModelProperty(value = "是否在轮询:0-否；1-是；")
    private Integer pollEnable;

    @ApiModelProperty(value = "是否需要密码:0-否；1-是；")
    private Integer needPassword;










}
