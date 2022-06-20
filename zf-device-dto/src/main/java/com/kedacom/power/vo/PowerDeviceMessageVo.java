package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PowerDeviceMessageVo
 * @Description 设备对应电压、温度等详细信息参数类
 * @Author zlf
 * @Date 2021/5/25 16:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "设备对应电压、温度等详细信息参数类")
public class PowerDeviceMessageVo implements Serializable {

    @ApiModelProperty(value = "电压值")
    private int voltage;

    @ApiModelProperty(value = "温度值")
    private int temperature;

    private List<PowerDeviceElectricityVo> electricities;

}
