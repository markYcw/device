package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:29
 * @description 获取刻录任务
 */
@Data
public class GetBurnTaskRequestVo extends SvrRequestDto{

    @NotBlank(message = "开始时间不能为空")
    @ApiModelProperty(value = "开始时间，如：20200827120000",required = true)
    private String startTime;

    @NotBlank(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间，如：20200827130000",required = true)
    private String endTime;

    @NotNull(message = "查询起始索引不能为空")
    @ApiModelProperty(value = "查询起始索引",required = true)
    private Integer queryIndex;

    @NotNull(message = "查询总数不能为空")
    @ApiModelProperty(value = "查询总数，最大值为16",required = true)
    private Integer queryCount;


}
