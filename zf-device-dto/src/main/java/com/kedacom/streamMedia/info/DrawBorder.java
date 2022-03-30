package com.kedacom.streamMedia.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/2/21 9:53
 */
@Data
@ApiModel(value = "绘制边框")
public class DrawBorder implements Serializable {

    @ApiModelProperty(value = "是否绘制边框，默认0，\n" +
            "0-不绘制\n" +
            "1-绘制")
    private Integer show_border = 0;

    @ApiModelProperty(value = "边框的像素值，默认2，\n" +
            "取值范围为[1,8]")
    private Integer border_width = 2;

    @ApiModelProperty(value = "颜色，三原色#RGB格式, 十六进制表示。\n" +
            "默认白色（#FFFFFF）。")
    private String border_color = "#FFFFFF";

}
