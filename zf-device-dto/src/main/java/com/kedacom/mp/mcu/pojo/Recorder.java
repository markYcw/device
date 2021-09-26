package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/16 10:52
 * @description 录像设置
 */
@Data
@ApiModel(description =  "录像设置")
public class Recorder implements Serializable {

    @NotNull(message = "发布模式 不能为空")
    @ApiModelProperty(value = "发布模式:0-不发布；1-发布；",required = true)
    private Integer publishMode;

    @NotNull(message = "是否内容共享录像 不能为空")
    @ApiModelProperty(value = "是否内容共享录像:0-否；1-是；",required = true)
    private Integer dualStream;

    @NotNull(message = "是否支持免登陆观看直播 不能为空")
    @ApiModelProperty(value = "是否支持免登陆观看直播:0-不支持；1-支持；",required = true)
    private Integer anonymous;

    @NotNull(message = "录像模式 不能为空")
    @ApiModelProperty(value = "录像模式:1-录像；2-直播；3-录像+直播；",required = true)
    private Integer recorderMode;

    @NotNull(message = "VRS的moid 不能为空")
    @ApiModelProperty(value = "VRS的moid",required = true)
    private Integer vrsId;

}
