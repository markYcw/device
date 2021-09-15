package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 13:34
 * @description 补刻
 */
@Data
public class ReBurnDto extends SvrRequestDto{

    @ApiModelProperty("0：根据ID补刻 1：根据时间补刻")
    private Integer type;

    @ApiModelProperty("刻录任务ID 补刻需要刻录任务ID或者开始结束时间。")
    private Integer burnTaskId;

    @ApiModelProperty("开始时间，如：20200827120000")
    private String startTime;

    @ApiModelProperty("结束时间，如：20200827130000")
    private String endTime;

    @ApiModelProperty("刻录模式 0：双盘同步 1：循环刻录")
    private Integer burnMode;


}
