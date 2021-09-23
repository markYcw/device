package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hxj
 * @date: 2021/8/17 11:06
 * @description 调节音量入参
 */
@Data
@ApiModel(description =  "调节音量入参")
public class McuVolumeDTO extends McuRequestDTO {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：输出，1：输入", required = true)
    private Integer type;

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @NotBlank(message = "终端id不能为空")
    @ApiModelProperty(value = "终端id", required = true)
    private String mtId;

    @NotNull(message = "音量值不能为空")
    @ApiModelProperty(value = "音量值:Pcmt 0~255;普通keda终端 0~32", required = true)
    private Integer volume;

}