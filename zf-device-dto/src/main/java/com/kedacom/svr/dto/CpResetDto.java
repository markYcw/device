package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 11:08
 * @description 修改编码器预置位
 */
@Data
public class CpResetDto extends SvrRequestDto{

    @NotNull(message = "通道ID不能为空")
    @ApiModelProperty(value = "通道ID",required = true)
    private Integer chnId;

    @NotNull(message = "预置位信息不能为空")
    @ApiModelProperty(value = "预置位1-255",required = true)
    private Integer preset;

}
