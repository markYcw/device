package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 时间叠加
 */
@Data
public class Time {

    @ApiModelProperty("是否显示")
    private Integer showOsd;

    @ApiModelProperty("叠加位置x")
    private Integer xPos;

    @ApiModelProperty("叠加位置y")
    private Integer yPos;

    @ApiModelProperty("叠加行数 0: 叠加到一行 1: 叠加到两行")
    private Integer timeStyle;


}
