package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 案件叠加
 */
@Data
public class Case {

    @ApiModelProperty("是否显示")
    private Integer showOsd;

    @ApiModelProperty("叠加位置x")
    private Integer xPos;

    @ApiModelProperty("叠加位置y")
    private Integer yPos;

    @ApiModelProperty("持续时间")
    private Integer duration;


}
