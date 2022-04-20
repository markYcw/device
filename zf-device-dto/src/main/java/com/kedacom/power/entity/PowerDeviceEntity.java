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

    /**
     * 电源设备数据库Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 设备类型（1：RK100；2：Bwant-IPM-08）
     */
    private Integer type;

    /**
     * 电源设备名称
     */
    private String name;

    /**
     * 设备IP地址
     */
    private String ip;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 设备MAC地址
     */
    private String mac;

    /**
     * 电源通道数量
     */
    @ApiModelProperty(value = "电源通道数量")
    private Integer channels;

    /**
     * 使用状态（0：关；1：开）
     */
    private Integer state;

    /**
     * RK100设备序列号
     */
    private String deviceSn;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NOT_NULL)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
