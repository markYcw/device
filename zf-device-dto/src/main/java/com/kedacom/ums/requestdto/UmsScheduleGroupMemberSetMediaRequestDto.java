package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(description =  "设置调度组成员媒体源信息请求参数类")
public class UmsScheduleGroupMemberSetMediaRequestDto implements Serializable {

    @ApiModelProperty(value = "调度组成员设备类型")
    private String deviceType;

    @ApiModelProperty(value = "调度组成员设备Id")
    private String deviceId;

    @ApiModelProperty(value = "0:不启用该字段  1：停止所有音频源  2：恢复所有音频源")
    private Integer stopAudioSrcurce;

    @ApiModelProperty(value = "0:不启用该字段  1：停止所有视频源  2：恢复所有视频源")
    private Integer stopVideoSrcurce;

    @ApiModelProperty(value = "当前成员的音频媒体源信息:设备类型")
    private String audioSrcDeviceType;

    @ApiModelProperty(value = "当前成员的音频媒体源信息:设备Id")
    private String audioSrcDeviceId;

    @ApiModelProperty(value = "当前成员的视频媒体源信息:设备类型")
    private String videoSrcDeviceType;

    @ApiModelProperty(value = "当前成员的视频媒体源信息:设备Id")
    private String videoSrcDeviceId;

    @ApiModelProperty(value = "当前成员的双流媒体源信息:设备类型")
    private String dualStreamDeviceType;

    @ApiModelProperty(value = "当前成员的双流媒体源信息:设备Id")
    private String dualStreamDeviceId;

}
