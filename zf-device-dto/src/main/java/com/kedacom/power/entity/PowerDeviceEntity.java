package com.kedacom.power.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 电源设备信息表
 * </p>
 *
 * @author zhanglongfei
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("km_power_device")
@Builder
public class PowerDeviceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "设备类型（1：RK100；2：Bwant-IPM-08）")
    private Integer type;

    @ApiModelProperty(value = "电源设备名称")
    private String name;

    @ApiModelProperty(value = "设备IP地址")
    private String ip;

    @ApiModelProperty(value = "端口号")
    private Integer port;

    @ApiModelProperty(value = "设备MAC地址")
    private String mac;

    @ApiModelProperty(value = "网关")
    private String devGatewayIp;

    @ApiModelProperty(value = "子网掩码")
    private String devIpMask;

    @ApiModelProperty(value = "服务器ip")
    private String desIp;

    @ApiModelProperty(value = "服务器端口")
    private Integer desPort;

    @ApiModelProperty(value = "电源通道数量")
    private Integer channels;

    @ApiModelProperty(value = "使用状态（0：关；1：开）")
    private Integer state;

    @ApiModelProperty(value = "使用状态（0：关；1：开）")
    private String deviceSn;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NOT_NULL)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
