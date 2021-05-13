package com.kedacom.ums.responsedto;

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
@ApiModel(value = "查询调度组广播源响应参数类")
public class UmsScheduleGroupQueryBroadcastResponseDto implements Serializable {

    @ApiModelProperty(value = "调度组Id")
    private String groupId;

    @ApiModelProperty(value = "需要被广播的音频设备源 设备类型")
    private String audioSrcDevType;

    @ApiModelProperty(value = "需要被广播的音频设备源 设备Id")
    private String audioSrcDevId;

    @ApiModelProperty(value = "需要被广播的视频设备源 设备类型")
    private String videoSrcDevType;

    @ApiModelProperty(value = "需要被广播的视频设备源 设备Id")
    private String videoSrcDevId;

    @ApiModelProperty(value = "视频双流 需要被广播的视频设备源 设备类型")
    private String dualVideoSrcDevType;

    @ApiModelProperty(value = "视频双流 需要被广播的视频设备源 设备Id")
    private String dualVideoSrcDevId;

}
