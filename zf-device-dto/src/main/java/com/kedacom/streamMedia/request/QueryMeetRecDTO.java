package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/21 14:05
 */
@Data
@ApiModel(description = "查询会议录像记录入参")
public class QueryMeetRecDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "通道列表不能为空")
    @ApiModelProperty(value = "通道列表")
    private String chnIds;

    @ApiModelProperty(value = "开始时间time_t，时间戳")
    private Long begin;

    @ApiModelProperty(value = "结束时间time_t，时间戳")
    private Long end;

    @ApiModelProperty(value = "开始索引")
    private Integer startIndex;

    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
