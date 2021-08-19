package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:08
 * @description 终端双流控制向中间件请求参数
 */
@Data
@ApiModel(value = "终端双流控制向中间件请求参数")
public class McuDualRequest implements Serializable {

    @ApiModelProperty(value = "0：开始，1：停止")
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "终端ip或者e164号", required = true)
    private String mtId;

}
