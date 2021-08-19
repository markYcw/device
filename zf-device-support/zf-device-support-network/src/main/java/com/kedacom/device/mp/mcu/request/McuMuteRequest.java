package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:05
 * @description 哑音向中间件请求参数
 */
@Data
@ApiModel(value = "哑音向中间件请求参数")
public class McuMuteRequest implements Serializable {

    @ApiModelProperty(value = "0：单个，1：全场", required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "终端ip或者e164号", required = true)
    private String mtId;

    @ApiModelProperty(value = "0：哑音，1：非哑音", required = true)
    private Integer state;

}
