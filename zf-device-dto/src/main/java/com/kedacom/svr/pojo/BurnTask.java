package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:50
 * @description 刻录任务
 */
@Data
public class BurnTask {

    @ApiModelProperty("刻录任务ID")
    private Integer burnTaskId;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("持续时间")
    private Integer continueTime;


}
