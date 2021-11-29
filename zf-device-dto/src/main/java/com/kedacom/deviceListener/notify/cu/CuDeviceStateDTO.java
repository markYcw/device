package com.kedacom.deviceListener.notify.cu;

import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/29 10:39
 * @description
 */
@Data
@ApiModel(description = "监控平台设备状态通知")
public class CuDeviceStateDTO extends DeviceNotifyRequestDTO {

    @ApiModelProperty("设备puId")
    private String puId;

    @ApiModelProperty("设备在线状态 0离线 1在线")
    private Integer online;
}
