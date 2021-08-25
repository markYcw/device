package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/13 13:41
 * @description 获取会议列表向中间件请求参数
 */
@Data
@ApiModel(description =  "获取会议列表向中间件请求参数")
public class McuConfsRequest implements Serializable {

    @ApiModelProperty(value = "本次开始查询索引", required = true)
    private Integer start;

    @ApiModelProperty(value = "本次查询总数", required = true)
    private Integer count;

}
