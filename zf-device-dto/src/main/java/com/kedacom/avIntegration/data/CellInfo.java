package com.kedacom.avIntegration.data;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 14:28
 */
@Data
@ApiModel("大屏布局坐标")
public class CellInfo implements Serializable {

    @ApiModelProperty(value = "窗口起始X坐标")
    private Integer x;

    @ApiModelProperty(value = "窗口起始Y坐标")
    private Integer y;

    @ApiModelProperty(value = "窗口起始W坐标")
    private Integer w;

    @ApiModelProperty(value = "窗口起始Y坐标")
    private Integer h;

}
