package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 13:59
 * @description
 */
@ApiModel(description = "控制音频功率上报")
@Data
public class CtrlAudioActDTO {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "设备id不能为空")
    @ApiModelProperty(value = "设备ID", required = true)
    private String deviceID;

    @NotNull(message = "控制类型不能为空")
    @ApiModelProperty(value = "控制类型 0：开始 1：停止", required = true)
    private Integer type;

}
