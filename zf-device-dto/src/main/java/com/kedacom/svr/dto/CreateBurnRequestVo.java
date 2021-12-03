package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:20
 * @description 创建刻录任务
 */
@Data
public class CreateBurnRequestVo extends SvrRequestDto{

    @NotBlank(message = "开始时间不能为空")
    @ApiModelProperty(value = "开始时间，如：20200827120000",required = true)
    private String startTime;

    @NotBlank(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间，如：20200827130000",required = true)
    private String endTime;

}
