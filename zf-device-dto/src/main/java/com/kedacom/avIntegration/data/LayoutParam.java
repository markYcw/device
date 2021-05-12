package com.kedacom.avIntegration.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 15:52
 */
@Data
@ApiModel("设置预案布局入参-窗口数组")
public class LayoutParam implements Serializable {

    /**
     * 等分布局参数
     */
    @NotNull(message = "单元格行数不能为空")
    @ApiModelProperty(value = "单元格行数 - 必填，预案画面虚拟切分小单元格行数")
    private Integer cell_row;

    @NotNull(message = "单元格列数不能为空")
    @ApiModelProperty(value = "单元格列数 - 必填，预案画面虚拟切分小单元格列数")
    private Integer cell_col;

    /**
     * 自定义布局参数
     */
    @NotNull(message = "解码器模式大屏窗口索引不能为空")
    @ApiModelProperty(value = "解码器模式大屏窗口索引 - 必填，大屏为解码器模式时，融合调度1.6以上版本该字段有效")
    private Integer cell_index;

    @NotNull(message = "窗口行起始位置不能为空")
    @ApiModelProperty(value = "窗口行起始位置 - 必填，单位：像素")
    private Integer x;

    @NotNull(message = "窗口列起始位置不能为空")
    @ApiModelProperty(value = "窗口列起始位置 - 必填，单位：像素")
    private Integer y;

    @NotNull(message = "窗口宽度不能为空")
    @ApiModelProperty(value = "窗口宽度 - 必填，单位：像素")
    private Integer w;

    @NotNull(message = "窗口高度不能为空")
    @ApiModelProperty(value = "窗口高度 - 必填，单位：像素")
    private Integer h;

}
