package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 19:44
 */
@Data
@ApiModel(description = "查询录像记录入参")
public class QueryRecDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "具体设备ID不能为空")
    @ApiModelProperty("具体设备ID")
    private String deviceID;

    @ApiModelProperty(value = "域ID。*在跨域访问场景必填" +
            "deviceID不为空时选填，表示交换节点（resourceID）所在的流媒体微服务和目标设备不在同一网络环境中，且需要通过目标设备所在域的指定流媒体微服务（nmedia_id）才能正确获取目标设备的音视频码流。" +
            "若nmedia_id=0或不填，表示交换节点所在的流媒体微服务和目标视频源在同一网络中，通过本流媒体微服务上交换节点即可获取到目标设备的音视频码流")
    private String nmedia_id;

    @NotBlank(message = "查询录像的开始时间不能为空")
    @ApiModelProperty("查询录像的开始时间，日期格式为:YYYY-MM-DDThh:mm:ss，如：2020-10-01T00:00:00(建议每次查询的录像时间段不大于2天)")
    private String start_time;

    @NotBlank(message = "查询录像的结束时间不能为空")
    @ApiModelProperty("查询录像的结束时间，日期格式为:YYYY-MM-DDThh:mm:ss，如：2020-10-01T00:00:00(建议每次查询的录像时间段不大于2天)")
    private String end_time;

    @ApiModelProperty("录像模糊查询:0-不进行模糊查询（默认），下需要指定查询位置;1-进行模糊查询，同时返回平台和前端检索结果")
    private Integer indistinct_query;

    @ApiModelProperty("在indistinct_query=0时需要指定是否前端查询:0-不是前端查询（默认）;1-查询前端录像记录")
    private Integer pu_query;

    @ApiModelProperty("按事件类型查询:all-所有类型(默认);alarm-告警录像;manual-手动录像;plan-计划录像;time-定时录像")
    private String query_type;

}
