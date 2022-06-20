package com.kedacom.power.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Data
@ApiModel(value = "返回给查询方参数")
public class Device {

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * mac地址
     */
    private String macAddr;

    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * 通道数量
     */
    private int channels;

}
