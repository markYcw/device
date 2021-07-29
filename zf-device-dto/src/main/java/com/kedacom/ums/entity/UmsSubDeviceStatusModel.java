package com.kedacom.ums.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 设备状态变更通知类
 *
 * @author van.shu
 * @create 2020/9/25 15:03
 * @see UmsProducerTopic#DEVICE_STATUS_CHANGE linked topic
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("统一设备状态通知类")
public class UmsSubDeviceStatusModel implements Serializable {

    private static final long serialVersionUID = -3043800208734953292L;

    @ApiModelProperty("设备唯一Id")
    private String devId;

    @ApiModelProperty("设备父Id")
    private String parentId;

    @ApiModelProperty("设备状态（0：在线 1：离线 2：故障）")
    private Integer devStatus;

    @ApiModelProperty("时间戳")
    private long timeStamp;

}
