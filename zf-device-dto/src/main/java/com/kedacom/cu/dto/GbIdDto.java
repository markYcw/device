package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 16:33
 * @description
 */
@ToString(callSuper = true)
@Data
public class GbIdDto extends CuRequestDto {

    @ApiModelProperty(value = "平台域ID",required = true)
    @NotBlank(message = "平台域ID不能为空")
    private String domain;

    @ApiModelProperty(value = "设备号",required = true)
    @NotBlank(message = "设备号不能为空")
    private String puId;

    @ApiModelProperty(value = "通道号",required = true)
    @NotNull(message = "通道号不能为空")
    private Integer chn;

    @ApiModelProperty(value = "视频源号",required = true)
    @NotNull(message = "视频源号不能为空")
    private Integer src;


}
