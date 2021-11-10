package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/10
 */
@Data
public class ControlPtzDto implements Serializable {

    @ApiModelProperty("设备id")
    @NotBlank(message = "设备id不能为空")
    private String puId;

    @ApiModelProperty("通道号")
    @NotEmpty(message = "通道号不能为空")
    private Integer chn;

    @ApiModelProperty("ptz命令id")
    @NotEmpty(message = "ptz命令id不能为空")
    private Integer ptzCmd;

    @ApiModelProperty("步长1-15")
    @NotEmpty(message = "步长1-15不能为空")
    private Integer range;

    @ApiModelProperty("级别")
    @NotEmpty(message = "级别不能为空")
    private Integer level;

    @ApiModelProperty("占用时间0-30秒")
    @NotEmpty(message = "占用时间0-30秒不能为空")
    private Integer holdTime;

}
