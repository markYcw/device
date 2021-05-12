package com.kedacom.avIntegration.request.decoder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class StyleConfigChnInfo implements Serializable {

    @NotBlank(message = "解码通道ID不能为空")
    @ApiModelProperty(value = "解码通道ID")
    private String chnid;

    @NotNull(message = "解码画面风格不能为空")
    @ApiModelProperty(value = "解码画面风格 - 1=一画面;2=二画面;3=三画面;4=四画面;9=九画面;16=十六画面")
    private Integer style;

    @ApiModelProperty(value = "最大解码能力 - 当前解码通道数")
    private Integer maxchn;

}
