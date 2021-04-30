package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 19:44
 */
@Data
public class QueryrecRequestDTO implements Serializable {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("域ID、在跨域访问场景必填")
    private String nmediaId;

    @ApiModelProperty("查询录像的开始时间，日期格式为:YYYY-MM-DDThh:mm:ss，如：2020-10-01T00:00:00(建议每次查询的录像时间段不大于2天)")
    private String startTime;

    @ApiModelProperty("查询录像的结束时间，日期格式为:YYYY-MM-DDThh:mm:ss，如：2020-10-01T00:00:00(建议每次查询的录像时间段不大于2天)")
    private String endTime;

    @ApiModelProperty("录像模糊查询:0-不进行模糊查询（默认），下需要指定查询位置;1-进行模糊查询，同时返回平台和前端检索结果")
    private Integer indistinctQuery;

    @ApiModelProperty("在indistinct_query=0时需要指定是否前端查询:0-不是前端查询（默认）;1-查询前端录像记录")
    private Integer puQuery;

    @ApiModelProperty("按事件类型查询:all-所有类型(默认);alarm-告警录像;manual-手动录像;plan-计划录像;time-定时录像")
    private String queryType;

}
