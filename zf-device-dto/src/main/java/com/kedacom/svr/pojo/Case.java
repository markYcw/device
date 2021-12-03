package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 案件叠加
 */
@Data
public class Case {

    @NotNull(message = "是否显示不能为空")
    @ApiModelProperty(value = "是否显示",required = true)
    private Integer showOsd;

    @NotNull(message = "叠加位置x不能为空")
    @ApiModelProperty(value = "叠加位置x",required = true)
    private Integer xPos;

    @NotNull(message = "叠加位置y不能为空")
    @ApiModelProperty(value = "叠加位置y",required = true)
    private Integer yPos;

    @NotNull(message = "持续时间不能为空")
    @ApiModelProperty(value = "持续时间",required = true)
    private Integer duration;


}
