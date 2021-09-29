package com.kedacom.device.mp.mcu.request;

import com.kedacom.mp.mcu.pojo.MtSrc;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:57
 * @description 开始/停止码流交换向中间件请求参数
 */
@Data
@ApiModel(description =  "开始/停止码流交换向中间件请求参数")
public class McuExchangeRequest implements Serializable {

    @ApiModelProperty(value = "0：开始，1：停止 ", required = true)
    private Integer type;

    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "选看模式 1：视频 2：音频 3：音视频")
    private Integer mode;

    @ApiModelProperty(value = "选看源，开始时必填")
    private MtSrc mtSrc;

    @ApiModelProperty(value = "目的终端号(选看源类型为画面合成时, 必须为主席终端号) 最大字符长度：48个字节")
    private String mtDst;

}
