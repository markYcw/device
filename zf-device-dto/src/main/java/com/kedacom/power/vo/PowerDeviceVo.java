package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName PowerDeviceVo
 * @Description 电源设备信息参数类
 * @Author zlf
 * @Date 2021/5/25 16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "电源设备信息参数类")
public class PowerDeviceVo implements Serializable {

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备mac地址")
    private String macAddr;

    @ApiModelProperty(value = "设备ip地址")
    private String ipAddr;
}
