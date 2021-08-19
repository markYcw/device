package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 10:59
 * @description 设置/取消发言人向中间件请求参数
 */
@Data
@ApiModel(value = "设置/取消发言人向中间件请求参数")
public class McuSpeakerRequest implements Serializable {

    @ApiModelProperty(value = "0：设置，1：取消",required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码",required = true)
    private String confId;

    @ApiModelProperty(value = "终端ip或者e164号,设置时必填")
    private String mtId;

}
