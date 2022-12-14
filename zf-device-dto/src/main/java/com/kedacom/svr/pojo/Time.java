package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 时间叠加
 */
@Data
public class Time {

    @NotNull(message = "是否显示不能为空")
    @ApiModelProperty(value = "是否显示",required = true)
    private Integer showOsd;

    @NotNull(message = "叠加位置x不能为空")
    @ApiModelProperty(value = "叠加位置x",required = true)
    private Integer xPos;

    @NotNull(message = "叠加位置y不能为空")
    @ApiModelProperty(value = "叠加位置y",required = true)
    private Integer yPos;

    @NotNull(message = "叠加行数不能为空")
    @ApiModelProperty(value = "叠加行数 0: 叠加到一行 1: 叠加到两行",required = true)
    private Integer timeStyle;


}
