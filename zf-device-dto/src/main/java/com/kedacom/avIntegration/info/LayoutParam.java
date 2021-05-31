package com.kedacom.avIntegration.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:52
 */
@Data
@ApiModel("设置预案布局入参-窗口数组")
public class LayoutParam implements Serializable {


    @ApiModelProperty(value = "等分布局下必填:一个大屏被切割成很多个小块，窗口是占用了多少个小块、wnd是窗口，内容是小块的索引")
    private List<Integer> wnds;

    @ApiModelProperty(value = "自定义布局必填:解码器模式大屏窗口索引 - 必填，大屏为解码器模式时，融合调度1.6以上版本该字段有效")
    private Integer cell_index;

    @ApiModelProperty(value = "自定义布局必填:窗口行起始位置 - 必填，单位：像素")
    private Integer x;

    @ApiModelProperty(value = "自定义布局必填:窗口列起始位置 - 必填，单位：像素")
    private Integer y;

    @ApiModelProperty(value = "自定义布局必填:窗口宽度 - 必填，单位：像素")
    private Integer w;

    @ApiModelProperty(value = "自定义布局必填:窗口高度 - 必填，单位：像素")
    private Integer h;

}
