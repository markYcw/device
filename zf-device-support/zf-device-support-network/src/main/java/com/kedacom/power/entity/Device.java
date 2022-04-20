package com.kedacom.power.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author gaoteng
 * @version v1.0
 * @date 2021/8/12 14:45
 * @description
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
