package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "设置调度组广播源请求参数类")
public class UmsScheduleGroupSetBroadcastRequestDto implements Serializable {

    @NotEmpty(message = "统一平台Id不能为空")
    @ApiModelProperty(value = "统一平台Id")
    private String umsId;

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
