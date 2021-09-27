package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/17 11:05
 * @description 哑音入参
 */
@Data
@ApiModel(description =  "哑音入参")
public class McuMuteDTO extends McuRequestDTO {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：单个，1：全场", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "终端id")
    private String mtId;

    @NotNull(message = "哑音状态不能为空")
    @ApiModelProperty(value = "0：哑音，1：非哑音", required = true)
    private Integer state;

}
