package com.kedacom.newMedia.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/4/2 15:37
 * @description
 */
@Data
public class NewMediaLoginDto implements Serializable {

    @ApiModelProperty(value = "设备类型, 新媒体平台为7")
    private Integer devType;

    @ApiModelProperty(value = "一机一档ip")
    private String devIp;

    @ApiModelProperty(value = "一机一档端口")
    private Integer devPort;

    @ApiModelProperty(value = "一机一档设备状态订阅ip+端口，多个中间用，隔开")
    private String notifyIp;

    @ApiModelProperty(value = "媒体调度服务IP")
    private String mediaScheduleIp;

    @ApiModelProperty(value = "媒体调度服务端口")
    private Integer mediaSchedulePort;

    @ApiModelProperty(value = "流媒体服务IP")
    private String nmediaIp;

    @ApiModelProperty(value = "流媒体服务端口")
    private Integer nmediaPort;

    @ApiModelProperty(value = "录像服务端口")
    private Integer recPort;

    @ApiModelProperty(value = "主动上报的url")
    private String ntyUrl;
}
