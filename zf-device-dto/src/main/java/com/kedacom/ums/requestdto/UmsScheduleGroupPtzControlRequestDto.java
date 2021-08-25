package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(description =  "调度组PTZ控制请求参数类(DeviceID为设备国标id)")
public class UmsScheduleGroupPtzControlRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "调度组Id不能为空")
    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @NotBlank(message = "设备国标Id不能为空")
    @ApiModelProperty(value = "设备国标Id")
    private String DeviceID;

    @NotNull(message = "PTZ控制状态不能为空")
    @ApiModelProperty(value = "PTZ控制状态（0：开始；1：停止）")
    private Integer State;

    @NotNull(message = "PTZ控制类型不能为空")
    @ApiModelProperty(value = "PTZ控制类型（1-上；2-下；3-左；4-右；5-上左；6-上右；7-下左；8-下右；9-视野小；10-视野大；11-调焦短；12-调焦长；13-亮度加；14-亮度减）")
    private Integer PtzCtrlType;

}
