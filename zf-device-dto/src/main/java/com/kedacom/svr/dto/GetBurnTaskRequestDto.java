package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:29
 * @description 获取刻录任务
 */
@Data
public class GetBurnTaskRequestDto extends SvrRequestDto{

    @ApiModelProperty(value = "开始时间，如：例如：2021-09-09T09:30:29.0000",required = true)
    private String startTime;

    @ApiModelProperty(value = "结束时间，如：例如：2021-09-09T09:30:29.0000",required = true)
    private String endTime;

    @NotNull(message = "查询起始索引不能为空")
    @ApiModelProperty(value = "查询起始索引",required = true)
    private Integer queryIndex;

    @NotNull(message = "查询总数不能为空")
    @ApiModelProperty(value = "查询总数，最大值为16",required = true)
    private Integer queryCount;


}
