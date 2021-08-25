package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/17 11:01
 * @description 设置/取消主席入参
 */
@Data
@ApiModel(description =  "设置/取消主席入参")
public class McuChairmanDTO extends McuRequestDTO {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：设置，1：取消",required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码",required = true)
    private String confId;

    @ApiModelProperty(value = "终端ip或者e164号,设置时必填")
    private String mtId;

}
