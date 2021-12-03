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
@ApiModel(description = "获取分组请求体")
public class CuGroupDto extends CuRequestDto{

    @NotBlank(message = "设备分组ID不能为空")
    @ApiModelProperty(value = "分组ID",required = true)
    private String groupId;

}
