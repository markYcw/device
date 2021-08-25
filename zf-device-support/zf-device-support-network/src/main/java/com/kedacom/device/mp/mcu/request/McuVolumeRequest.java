package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:06
 * @description 调节音量向中间件请求参数
 */
@Data
@ApiModel(description =  "调节音量向中间件请求参数")
public class McuVolumeRequest implements Serializable {

    @ApiModelProperty(value = "0：输出，1：输入", required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "终端ip或者e164号", required = true)
    private String mtId;

    @ApiModelProperty(value = "音量值:Pcmt 0~255;普通keda终端 0~32", required = true)
    private Integer volume;

}