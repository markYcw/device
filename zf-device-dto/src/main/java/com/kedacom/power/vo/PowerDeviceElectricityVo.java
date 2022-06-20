package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName PowerDeviceElectricityVo
 * @Description 设备通道对应电流等详细信息参数类
 * @Author zlf
 * @Date 2021/5/25 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "设备通道对应电流等详细信息参数类")
public class PowerDeviceElectricityVo implements Serializable {

    @ApiModelProperty(value = "通道编号")
    private int channel;

    @ApiModelProperty(value = "电流值")
    private float electricity;

}
