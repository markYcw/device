package com.kedacom.acl.network.ums.requestvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "调度组PTZ控制请求参数类")
public class UmsScheduleGroupPtzControlRequest implements Serializable {

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "设备Id")
    private String DeviceID;

    @ApiModelProperty(value = "PTZ控制状态（0：开始；1：停止）")
    private Integer State;

    @ApiModelProperty(value = "PTZ控制类型（1-上；2-下；3-左；4-右；5-上左；6-上右；7-下左；8-下右；9-视野小；10-视野大；11-调焦短；12-调焦长；13-亮度加；14-亮度减）")
    private Integer PtzCtrlType;

}
