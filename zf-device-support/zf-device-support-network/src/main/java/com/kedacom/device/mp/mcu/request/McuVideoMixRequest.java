package com.kedacom.device.mp.mcu.request;

import com.kedacom.mp.mcu.pojo.MixInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:16
 * @description 开始/停止画面合成向中间件请求参数
 */
@Data
@ApiModel(value = "开始/停止画面合成向中间件请求参数")
public class McuVideoMixRequest implements Serializable {

    @ApiModelProperty(value = "会议号码")
    private String confId;

    @ApiModelProperty(value = "合成参数，开始时必填")
    private MixInfo mixInfo;

}