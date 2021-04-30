package com.kedacom.streamMedia.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 14:13
 */
@Data
@ApiModel("画布内容")
public class DrawText implements Serializable {

    @ApiModelProperty("是否叠加文本信息，默认值 0。0-不叠加；1-叠加。")
    private Integer showText;

    @ApiModelProperty("字体大小。默认：26，单位：pt。")
    private Integer fontSize;

    @ApiModelProperty("字体颜色，三原色#RGB 格式, 十六进制表示。例：白色（#FFFFFF）。")
    private String fontColor;

    @ApiModelProperty("文本信息显示位置, 默认为: 1。0-左上角；1-左下角；2-右上角；3-右下角；4-底部中间。")
    private Integer textPos;


}
