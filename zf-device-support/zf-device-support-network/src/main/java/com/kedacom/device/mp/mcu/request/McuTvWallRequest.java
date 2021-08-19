package com.kedacom.device.mp.mcu.request;

import com.kedacom.mp.mcu.pojo.Chns;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:52
 * @description 开始/停止上电视墙向中间件请求参数
 */
@Data
@ApiModel(value = "开始/停止上电视墙向中间件请求参数")
public class McuTvWallRequest implements Serializable {

    @ApiModelProperty(value = "0：开始，1：停止 ", required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "电视墙id", required = true)
    private String tvWallId;

    @ApiModelProperty(value = "通道信息，开始时必填 ")
    private Chns chns;

}
