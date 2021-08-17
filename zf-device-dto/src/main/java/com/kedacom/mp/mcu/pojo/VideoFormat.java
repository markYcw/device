package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/14 16:03
 * @description 主视频格式列表
 */
@Data
@ApiModel(value = "主视频格式列表")
public class VideoFormat implements Serializable {

    @ApiModelProperty(value = "主视频格式:1- MPEG;2- H.261;3- H.263;4- H.264_HP;5- H.264_BP;6- H.265;7- H.263+;")
    private Integer format;

    @ApiModelProperty(value = "主视频分辨率:1-QCIF;2-CIF;3-4CIF;12-720P;13-1080P;14-WCIF;15-W4CIF;16-4k;")
    private Integer resolution;

    @ApiModelProperty(value = "帧率")
    private Integer frame;

    @ApiModelProperty(value = "码率")
    private Integer bitrate;

}
