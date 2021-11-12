package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/12
 */
@Data
public class RecBaseVo implements Serializable {

    @ApiModelProperty("平台ID")
    @NotEmpty(message = "平台ID不能为空")
    private Integer dbId;

    @ApiModelProperty("设备域")
    @NotBlank(message = "设备域不能为空")
    private String domain;

    @ApiModelProperty("设备id")
    @NotBlank(message = "设备id不能为空")
    private String puId;

    @ApiModelProperty("通道号")
    @NotEmpty(message = "通道号不能为空")
    private Integer chnId;

}
