package com.kedacom.power.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @Description 公共查询条件
 */
@Data
public abstract class PageReqVo {

    @ApiModelProperty(value = "当前页码，默认为第一页")
    @Min(value = 1, message = "页码数不合法")
    private Integer pageIndex = 1;

    @ApiModelProperty(value = "每页多少，默认为10条")
    @Min(value = 1, message = "每页条数不合法")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;

}
