package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName PowerDeviceUpdateVo
 * @Description 修改电源设备信息参数类
 * @Author zlf
 * @Date 2021/5/27 11:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "修改电源设备信息参数类")
public class PowerDeviceUpdateVo implements Serializable {

    @ApiModelProperty(value = "电源设备数据库Id", required = true)
    private Integer id;

    @ApiModelProperty(value = "设备类型（1：RK100；2：Bwant-IPM-08）", required = false)
    private Integer type;

    @ApiModelProperty(value = "电源设备名称", required = false)
    private String name;

    @ApiModelProperty(value = "设备IP地址", required = false)
    private String ip;

    @ApiModelProperty(value = "端口号，RK100类型时有效", required = false)
    private Integer port;

    @ApiModelProperty(value = "RK100设备序列号，RK100类型时有效", required = false)
    private String deviceSn;

    @ApiModelProperty(value = "设备MAC地址", required = false)
    private String mac;

}
