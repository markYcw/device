package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class TvPlayOpenLayoutData implements Serializable {

    @ApiModelProperty(value = "窗口ID - 只读")
    private Integer wndid;

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

    @ApiModelProperty(value = "信号参数")
    private List<TvPlayOpenChnInfo> chns;

}