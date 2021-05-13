package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "查询录像记录请求参数类")
public class UmsQueryRecListRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "umsId - 平台Id")
    private String umsId;

    @NotBlank(message = "媒体设备ID不能为空")
    @ApiModelProperty(value = "媒体设备ID。监控设备为国标ID，会议设备待定")
    private String mediaDevId;

    @ApiModelProperty(value = "域ID。*在跨域访问场景必填" +
            "deviceID不为空时选填，表示交换节点（resourceID）所在的流媒体微服务和目标设备不在同一网络环境中，且需要通过目标设备所在域的指定流媒体微服务（nmedia_id）才能正确获取目标设备的音视频码流。" +
            "若nmedia_id=0或不填，表示交换节点所在的流媒体微服务和目标视频源在同一网络中，通过本流媒体微服务上交换节点即可获取到目标设备的音视频码流")
    private Integer nmediaId;

    @ApiModelProperty(value = "起始时间. 2020-10-12 08:00:00")
    private String startTime;

    @ApiModelProperty(value = "结束时间. 2020-10-12 08:59:59")
    private String endTime;

    @ApiModelProperty(value = "录像模糊查询。0-不进行模糊查询（默认），下需要指定查询位置；1-进行模糊查询，同时返回平台和前端检索结果")
    private Integer indistinctQuery;

    @ApiModelProperty(value = "在indistinct_query=0时需要指定。是否前端查询。 0-不是前端查询（默认） 1-查询前端录像记录")
    private Integer puQuery;

    @ApiModelProperty(value = "按事件类型查询 all-所有类型（默认）;alarm-告警录像;manual-手动录像;plan-计划录像;time-定时录像")
    private String queryType;

}
