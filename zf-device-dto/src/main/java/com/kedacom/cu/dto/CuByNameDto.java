package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author ycw
 * @describe
 * @date 2022/04/13
 */
@Data
@ApiModel(description = "根据名称查询请求参数类")
public class CuByNameDto extends CuRequestDto implements Serializable {

    @ApiModelProperty(value = "设备名称",required = true)
    @NotBlank(message = "设备名称不能为空")
    private String name;
}
