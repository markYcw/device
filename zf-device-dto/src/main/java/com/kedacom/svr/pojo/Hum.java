package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 温湿度叠加
 */
@Data
public class Hum {

    @NotNull(message = "是否显示不能为空")
    @ApiModelProperty(value = "是否显示",required = true)
    private Integer showOsd;

    @NotNull(message = "叠加位置x不能为空")
    @ApiModelProperty(value = "叠加位置x",required = true)
    private Integer xPos;

    @NotNull(message = "叠加位置y不能为空")
    @ApiModelProperty(value = "叠加位置y",required = true)
    private Integer yPos;

    @ApiModelProperty("源通道ID 3.0暂不支持")
    private Integer srcChnId;

    @ApiModelProperty("是否显示整数 3.0暂不支持")
    private Integer displayInt;

    @ApiModelProperty("更新频率 3.0暂不支持")
    private Integer updateInterval;

}
