package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:20
 * @description 创建刻录任务
 */
@Data
public class CreateBurnDto extends SvrRequestDto{

    @ApiModelProperty("0：开始新建 1：停止新建")
    private Integer type;

    @ApiModelProperty("开始时间，如：20200827120000 ")
    private String startTime;

    @ApiModelProperty("结束时间，如：20200827130000")
    private String endTime;

}
