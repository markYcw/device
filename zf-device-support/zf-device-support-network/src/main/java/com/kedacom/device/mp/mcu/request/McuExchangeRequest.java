package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:57
 * @description 开始/停止码流交换向中间件请求参数
 */
@Data
@ApiModel(value = "开始/停止码流交换向中间件请求参数")
public class McuExchangeRequest implements Serializable {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：开始，1：停止 ", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "源终端e164号，开始时必填")
    private String mtSrcInfo;

    @ApiModelProperty(value = "目的终端e164号")
    private String mtDstInfo;

    @ApiModelProperty(value = "选看模式 1：视频 2：音频 3：音视频")
    private Integer mode;

}