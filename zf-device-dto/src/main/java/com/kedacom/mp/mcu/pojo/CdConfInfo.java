package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称",required = true)
    private String name;

    @NotNull(message = "会议时长不能为空")
    @ApiModelProperty(value = "会议时长 0为永久会议",required = true)
    private Integer duration;

    @NotNull(message = "会议码率不能为空")
    @ApiModelProperty(value = "会议码率 如：1024 2048等",required = true)
    private Integer bitrate;

    @NotNull(message = "会议免打扰不能为空")
    @ApiModelProperty(value = "会议免打扰:0-关闭；1-开启；",required = true)
    private Integer closedConf;

    @NotNull(message = "会议安全不能为空")
    @ApiModelProperty(value = "会议安全:0-公开会议；1-隐藏会议；",required = true)
    private Integer safeConf;

    @NotNull(message = "传输加密类型不能为空")
    @ApiModelProperty(value = "传输加密类型:0-不加密；2-AES加密；",required = true)
    private Integer encryptedType;

    @NotNull(message = "会议类型不能为空")
    @ApiModelProperty(value = "会议类型:0-传统会议；1-端口会议；",required = true)
    private Integer confType;

    @NotNull(message = "呼叫模式不能为空")
    @ApiModelProperty(value = "呼叫模式:0-手动呼叫；2-定时呼叫；",required = true)
    private Integer callMode;

    @NotNull(message = "呼叫次数不能为空")
    @ApiModelProperty(value = "呼叫次数",required = true)
    private Integer callTimes;

    @NotNull(message = "呼叫间隔不能为空")
    @ApiModelProperty(value = "呼叫间隔(秒)",required = true)
    private Integer callInterval;

    @NotNull(message = "是否开启初始化哑音不能为空")
    @ApiModelProperty(value = "是否开启初始化哑音:0-否；1-是；",required = true)
    private Integer mute;

    @NotNull(message = "是否开启初始化静音不能为空")
    @ApiModelProperty(value = "是否开启初始化静音:0-否；1-是；",required = true)
    private Integer silence;

    @NotNull(message = "视频质量不能为空")
    @ApiModelProperty(value = "视频质量,其中租赁环境默认设为质量优先，自建环境以api下参为准 0-质量优先；1-速度优先；",required = true)
    private Integer videoQuality;

    @NotNull(message = "传输加密AES加密密钥不能为空")
    @ApiModelProperty(value = "传输加密AES加密密钥 最大字符长度：16字节",required = true)
    private String encryptedKey;

    @NotNull(message = "双流权限不能为空")
    @ApiModelProperty(value = "双流权限:0-发言会场；1-任意会场",required = true)
    private Integer dualMode;

    @NotNull(message = "是否开启语音激励不能为空")
    @ApiModelProperty(value = "是否开启语音激励:0-否；1-是；",required = true)
    private Integer voiceActivityDetection;

    @NotNull(message = "级联模式不能为空")
    @ApiModelProperty(value = "级联模式:0-简单级联；1-合并级联；",required = true)
    private Integer cascadeMode;

    @NotNull(message = "是否级联上传不能为空")
    @ApiModelProperty(value = "是否级联上传:0-否；1-是；",required = true)
    private Integer cascadeUpload;

    @NotNull(message = "是否级联回传不能为空")
    @ApiModelProperty(value = "是否级联回传:0-否;1-是；",required = true)
    private Integer cascadeReturn;

    @NotNull(message = "级联回传带宽参数不能为空")
    @ApiModelProperty(value = "级联回传带宽参数",required = true)
    private Integer cascadeReturnPara;

    @NotNull(message = "是否来宾会议室不能为空")
    @ApiModelProperty(value = "是否来宾会议室: 1-是；:0-否；1-是；",required = true)
    private Integer publicConf;

    @NotNull(message = "最大与会终端数不能为空")
    @ApiModelProperty(value = "最大与会终端数:8-小型8方会议；32-32方会议；64-64方会议；192-大型192方会议；",required = true)
    private Integer maxJoinMt;

    @NotNull(message = "会议中无终端时不能为空")
    @ApiModelProperty(value = "会议中无终端时，是否自动结会，永久会议时默认为0 0-否；1-是；",required = true)
    private Integer autoEnd;

    @NotNull(message = "预占资源不能为空")
    @ApiModelProperty(value = "预占资源:0-否；1-是；",required = true)
    private Integer preoccpuyResource;

    @NotNull(message = "归一重整不能为空")
    @ApiModelProperty(value = "归一重整:0-不使用;1-使用；",required = true)
    private Integer oneReforming;

    @ApiModelProperty(value = "发言人")
    @Valid
    private Speaker speaker;

    @ApiModelProperty(value = "主席")
    @Valid
    private Chairman chairman;

    @ApiModelProperty(value = "混音信息")
    @Valid
    private Mix mix;

    @ApiModelProperty(value = "主视频格式",required = true)
    @Valid
    private VideoFormat videoFormat;

    @ApiModelProperty(value = "参会成员 创建虚拟会议室时可填")
    @Valid
    private List<InviteMember> inviteMembers;

    @ApiModelProperty(value = "录像设置")
    @Valid
    private Recorder recorder;

}
