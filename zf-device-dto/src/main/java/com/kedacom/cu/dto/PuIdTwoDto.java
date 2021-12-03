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
public class PuIdTwoDto extends CuRequestDto {

    @ApiModelProperty(value = "国标id",required = true)
    @NotBlank(message = "国标id不能为空")
    private String gbId;


}
