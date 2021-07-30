package com.kedacom.msp.request.decoder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Position implements Serializable {

    @NotNull(message = "字幕显示位置X坐标不能为空")
    @ApiModelProperty(value = "字幕显示位置X坐标 - 必填，单位：像素")
    private Integer left;

    @NotNull(message = "字幕显示位置Y坐标不能为空")
    @ApiModelProperty(value = "字幕显示位置Y坐标 - 必填，单位：像素")
    private Integer top;

    @NotNull(message = "字幕宽度不能为空")
    @ApiModelProperty(value = "字幕宽度 - 必填，单位：像素")
    private Integer width;

    @NotNull(message = "字幕高度不能为空")
    @ApiModelProperty(value = "字幕高度 - 必填，单位：像素")
    private Integer height;

}
