package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/17 11:08
 * @description 终端双流控制入参
 */
@Data
@ApiModel(description =  "终端双流控制入参")
public class McuDualDTO extends McuRequestDTO {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：开始，1：停止",required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @NotBlank(message = "终端id不能为空")
    @ApiModelProperty(value = "终端id", required = true)
    private String mtId;

}
