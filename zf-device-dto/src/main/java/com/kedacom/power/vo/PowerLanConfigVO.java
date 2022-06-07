package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/6 16:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "局域网搜索-电源基本配置")
public class PowerLanConfigVO implements Serializable {

    @ApiModelProperty(value = "电源主键id")
    private Integer id;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备ip")
    private String ip;

    @ApiModelProperty(value = "网关")
    private String devGatewayIp;

    @ApiModelProperty(value = "子网掩码")
    private String devIpMask;

    @ApiModelProperty(value = "服务器ip")
    private String desIp;

    @ApiModelProperty(value = "服务器端口")
    private Integer desPort;

}
