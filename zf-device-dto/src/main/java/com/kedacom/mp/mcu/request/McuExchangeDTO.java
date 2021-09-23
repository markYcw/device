package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.pojo.MtSrcInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/17 13:57
 * @description 开始/停止码流交换入参
 */
@Data
@ApiModel(description =  "开始/停止码流交换入参")
public class McuExchangeDTO extends McuRequestDTO {

    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "0：开始，1：停止 ", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @NotNull(message = "型不能为空")
    @ApiModelProperty(value = "选看模式 1：视频 2：音频 3：音视频")
    private Integer mode;

    @ApiModelProperty(value = "选看源，开始时必填")
    private MtSrcInfo mtSrc;


}
