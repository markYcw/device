package com.kedacom.power.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
     * 使用状态（0：关；1：开）
     */
    private Integer state;

    /**
     * RK100设备序列号
     */
    private String deviceSn;


}
