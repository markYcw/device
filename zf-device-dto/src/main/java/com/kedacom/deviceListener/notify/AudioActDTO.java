package com.kedacom.deviceListener.notify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 17:38
 * @description
 */
@Data
@ApiModel(description = "音频功率通知")
public class AudioActDTO extends DeviceNotifyRequestDTO{

    @ApiModelProperty(value = "激励开关 0：关 1：开")
    private Integer ActCfg;

    @ApiModelProperty(value = "激励通道")
    private Integer ActChn;

    @ApiModelProperty(value = "功率")
    private Integer Power;

    @ApiModelProperty(value = "设备ID")
    private String DeviceID;

}
