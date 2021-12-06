package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/12
 */
@Data
public class RecBaseVo implements Serializable {

    @NotNull(message = "平台ID不能为空")
    @ApiModelProperty(value = "平台数据库ID", required = true)
    private Integer dbId;

    @NotBlank(message = "设备域ID不能为空")
    @ApiModelProperty(value = "设备域ID", required = true)
    private String domain;

    @NotBlank(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号", required = true)
    private String puId;

    @NotNull(message = "通道号不能为空")
    @ApiModelProperty(value = "通道号", required = true)
    private Integer chnId;

}
