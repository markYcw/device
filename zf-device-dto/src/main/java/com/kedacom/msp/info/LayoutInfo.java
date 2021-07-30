package com.kedacom.msp.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 16:00
 */
@Data
@ApiModel("查询预案布局应答-窗口布局参数")
public class LayoutInfo implements Serializable {

    @ApiModelProperty(value = "窗口ID - 只读")
    private Integer wnd_id;

    @ApiModelProperty(value = "窗口行起始位置")
    private Integer x;

    @ApiModelProperty(value = "窗口列起始位置")
    private Integer y;

    @ApiModelProperty(value = "窗口宽度")
    private Integer w;

    @ApiModelProperty(value = "窗口高度")
    private Integer h;

}
