package com.kedacom.msp.request.decoder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FontInfo implements Serializable {

    @NotNull(message = "字体大小不能为空")
    @ApiModelProperty(value = "字体大小 - 必填，单位：像素")
    private Integer size;

    @NotNull(message = "字体颜色G不能为空")
    @ApiModelProperty(value = "字体颜色G - 必填")
    private Integer green;

    @NotNull(message = "字体颜色R不能为空")
    @ApiModelProperty(value = "字体颜色R - 必填")
    private Integer red;

    @NotNull(message = "字体颜色B不能为空")
    @ApiModelProperty(value = "字体颜色B - 必填")
    private Integer blue;

}