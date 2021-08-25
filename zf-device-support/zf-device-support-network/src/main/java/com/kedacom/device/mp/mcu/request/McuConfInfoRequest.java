package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/14 15:10
 * @description 获取会议信息向中间件请求参数
 */
@Data
@ApiModel(description = "获取会议信息向中间件请求参数")
public class McuConfInfoRequest implements Serializable {

    @ApiModelProperty(value = "会议号码")
    private String confId;

}
