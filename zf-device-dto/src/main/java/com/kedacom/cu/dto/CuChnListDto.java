package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/29 17:09
 * @description
 */
@Data
@ApiModel(description = "获取设备通道集合")
public class CuChnListDto extends CuRequestDto{

    @NotBlank(message = "设备puId不能为空")
    @ApiModelProperty("设备puId")
    private String puId;

}
