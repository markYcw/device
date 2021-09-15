package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 16:01
 * @description 查询录像
 */
@Data
public class RecListDto extends SvrRequestDto{

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("开始时间，如：20200827120000")
    private String startTime;

    @ApiModelProperty("结束时间，如：20200827130000")
    private String endTime;

    @ApiModelProperty("查询起始索引")
    private Integer queryIndex;

    @ApiModelProperty("查询总数，最大值为16")
    private Integer queryCount;

}
