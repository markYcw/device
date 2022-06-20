package com.kedacom.power.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Data
@ApiModel(value = "返回给查询方参数")
public class LanDevice {

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "mac地址")
    private String macAddr;

    @ApiModelProperty(value = "ip地址")
    private String ipAddr;

    @ApiModelProperty(value = "是否添加，0：否 ，1：是")
    private Integer isAdd;

}
