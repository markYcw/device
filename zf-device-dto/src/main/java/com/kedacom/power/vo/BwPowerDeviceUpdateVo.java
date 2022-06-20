package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "修改电源设备信息参数类")
public class BwPowerDeviceUpdateVo implements Serializable {

    @ApiModelProperty(value = "电源设备数据库Id", required = true)
    private Integer id;

    @ApiModelProperty(value = "电源设备名称", required = false)
    private String name;

    @ApiModelProperty(value = "设备IP地址", required = false)
    private String ip;

    @ApiModelProperty(value = "设备MAC地址", required = false)
    private String mac;

}
