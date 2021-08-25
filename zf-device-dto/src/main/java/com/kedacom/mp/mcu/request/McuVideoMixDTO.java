package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.pojo.MixInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/17 11:16
 * @description 开始/停止画面合成入参
 */
@Data
@ApiModel(description =  "开始/停止画面合成入参")
public class McuVideoMixDTO extends McuRequestDTO {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：开始，1：停止", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码",required = true)
    private String confId;

    @ApiModelProperty(value = "合成参数，开始时必填")
    private MixInfo mixInfo;

}
