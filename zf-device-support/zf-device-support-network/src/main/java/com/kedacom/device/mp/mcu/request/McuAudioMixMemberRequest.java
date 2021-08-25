package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:24
 * @description 添加/删除混音成员向中间件请求参数
 */
@Data
@ApiModel(description =  "添加/删除混音成员向中间件请求参数")
public class McuAudioMixMemberRequest implements Serializable {

    @ApiModelProperty(value = "0：添加，1：删除", required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "终端ip或者e164号")
    private String mtId;

}
