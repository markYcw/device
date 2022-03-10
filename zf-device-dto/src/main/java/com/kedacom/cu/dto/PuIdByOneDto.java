package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 16:33
 * @description
 */
@ToString(callSuper = true)
@Data
public class PuIdByOneDto extends CuRequestDto {

    @ApiModelProperty(value = "平台1.0puId",required = true)
    @NotBlank(message = "puId不能为空")
    private String puId;


}
