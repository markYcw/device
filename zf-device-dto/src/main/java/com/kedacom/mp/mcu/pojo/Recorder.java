package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/16 10:52
 * @description 录像设置
 */
@Data
@ApiModel(value = "录像设置")
public class Recorder implements Serializable {

    @ApiModelProperty(value = "发布模式:0-不发布；1-发布；")
    private Integer publishMode;

    @ApiModelProperty(value = "是否内容共享录像:0-否；1-是；")
    private Integer dualStream;

    @ApiModelProperty(value = "是否支持免登陆观看直播:0-不支持；1-支持；")
    private Integer anonymous;

    @ApiModelProperty(value = "录像模式:1-录像；2-直播；3-录像+直播；")
    private Integer recorderMode;

    @ApiModelProperty(value = "VRS的moid")
    private Integer vrsId;

}
