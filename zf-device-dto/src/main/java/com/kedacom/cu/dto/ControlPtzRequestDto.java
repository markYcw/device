package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/10
 */
@Data
@ApiModel(description = "PTZ控制请求参数类")
public class ControlPtzRequestDto extends CuRequestDto implements Serializable {

    @NotBlank(message = "设备id不能为空")
    @ApiModelProperty(value = "设备id", required = true)
    private String puId;

    @NotNull(message = "通道号不能为空")
    @ApiModelProperty(value = "通道号", required = true)
    private Integer chn;

    @NotNull(message = "ptz命令id不能为空")
    @ApiModelProperty(value = "ptz命令id", required = true)
    private Integer ptzCmd;

    @NotNull(message = "步长1-15不能为空")
    @ApiModelProperty(value = "步长1-15", required = true)
    private Integer range;

    @NotNull(message = "级别不能为空")
    @ApiModelProperty(value = "级别", required = true)
    private Integer level;

    @NotNull(message = "占用时间0-30秒不能为空")
    @ApiModelProperty(value = "占用时间0-30秒", required = true)
    private Integer holdTime;

}
