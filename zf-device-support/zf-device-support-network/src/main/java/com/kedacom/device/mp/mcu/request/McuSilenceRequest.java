package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:03
 * @description 静音向中间件请求参数
 */
@Data
@ApiModel(value = "静音向中间件请求参数")
public class McuSilenceRequest implements Serializable {

    @ApiModelProperty(value = "0：单个，1：全场", required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "终端ip或者e164号", required = true)
    private String mtId;

    @ApiModelProperty(value = "0：静音，1：非静音", required = true)
    private Integer state;

}
