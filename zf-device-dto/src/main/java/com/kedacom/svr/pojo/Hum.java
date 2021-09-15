package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 温湿度叠加
 */
@Data
public class Hum {

    @ApiModelProperty("是否显示")
    private Integer showOsd;

    @ApiModelProperty("叠加位置x")
    private Integer xPos;

    @ApiModelProperty("叠加位置y")
    private Integer yPos;

    @ApiModelProperty("源通道ID")
    private Integer srcChnId;

    @ApiModelProperty("是否显示整数")
    private Integer displayInt;

    @ApiModelProperty("更新频率")
    private Integer updateInterval;

}
