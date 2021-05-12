package com.kedacom.avIntegration.request.decoder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class Subtitle implements Serializable {

    @ApiModelProperty(value = "画面编号 - 编号从0开始，0=第一画面")
    private Integer index;

    @NotBlank(message = "字幕的内容不能为空")
    @ApiModelProperty(value = "字幕的内容 - 必填", required = true)
    private String content;

    @ApiModelProperty(value = "字幕的内容")
    private Position position;

    @ApiModelProperty(value = "字体信息")
    private FontInfo fontinfo;

}