package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hxj
 * @date: 2021/8/17 11:18
 * @description 智能混音入参
 */
@Data
@ApiModel(value = "智能混音入参")
public class McuIntaudioMixDTO extends McuRequestDTO {

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码",required = true)
    private String confId;

}
