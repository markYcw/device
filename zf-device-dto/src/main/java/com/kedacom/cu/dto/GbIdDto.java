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

    @ApiModelProperty("平台域")
    @NotBlank(message = "平台域不能为空")
    private String domain;

    @ApiModelProperty("设备puId")
    @NotBlank(message = "puId不能为空")
    private String puId;

    @ApiModelProperty("通道号")
    @NotNull(message = "通道号不能为空")
    private Integer chn;

}
