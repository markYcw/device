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

    @NotEmpty(message = "平台ID不能为空")
    @ApiModelProperty(value = "平台ID", required = true)
    private Integer dbId;

    @NotBlank(message = "设备域不能为空")
    @ApiModelProperty(value = "设备域", required = true)
    private String domain;

    @NotBlank(message = "设备id不能为空")
    @ApiModelProperty(value = "设备id", required = true)
    private String puId;

    @NotEmpty(message = "通道号不能为空")
    @ApiModelProperty(value = "通道号", required = true)
    private Integer chnId;

}
