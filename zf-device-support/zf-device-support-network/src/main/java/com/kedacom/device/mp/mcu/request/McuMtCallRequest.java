package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 10:56
 * @description 呼叫/挂断终端向中间件请求参数
 */
@Data
@ApiModel(description =  "呼叫/挂断终端向中间件请求参数")
public class McuMtCallRequest implements Serializable {

    @ApiModelProperty(value = "0：呼叫，1：挂断")
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "终端ip或者e164号", required = true)
    private String mtId;

}
