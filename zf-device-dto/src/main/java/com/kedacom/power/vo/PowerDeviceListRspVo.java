package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName PowerDeviceListRspVo
 * @Description 条件查询电源设备信息返回参数类
 * @Author zlf
 * @Date 2021/5/27 13:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "条件查询电源设备信息返回参数类")
public class PowerDeviceListRspVo implements Serializable {

    /**
     * 电源设备数据库Id
     */
    @ApiModelProperty(value = "电源设备数据库Id")
    private Integer id;

    /**
     * 设备类型（1：RK100；2：Bwant-IPM-08）
     */
    @ApiModelProperty(value = "设备类型（1：RK100；2：Bwant-IPM-08）")
    private Integer type;

    /**
     * 电源设备名称
     */
    @ApiModelProperty(value = "电源设备名称")
    private String name;

    /**
     * 设备IP地址
     */
    @ApiModelProperty(value = "设备IP地址")
    private String ip;

    /**
     * 端口号
     */
    @ApiModelProperty(value = "端口号")
    private Integer port;

    /**
     * 设备MAC地址
     */
    @ApiModelProperty(value = "设备MAC地址")
    private String mac;

    /**
     * 使用状态（0：关；1：开）
     */
    @ApiModelProperty(value = "使用状态（0：关；1：开）")
    private Integer state;

    /**
     * 电源通道数量
     */
    @ApiModelProperty(value = "电源通道数量")
    private Integer channels;

    /**
     * RK100设备序列号
     */
    @ApiModelProperty(value = "RK100设备序列号")
    private String deviceSn;

}
