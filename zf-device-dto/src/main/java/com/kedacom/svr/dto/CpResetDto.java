package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 11:08
 * @description 修改编码器预置位
 */
@Data
public class CpResetDto extends SvrRequestDto{

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("预置位1-255")
    private Integer preset;

}
