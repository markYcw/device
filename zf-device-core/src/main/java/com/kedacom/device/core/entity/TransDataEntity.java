package com.kedacom.device.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/20 15:37
 */
@ToString(callSuper = true)
@Data
@ApiModel(description = "接收透明通道数据通知实体")
public class TransDataEntity implements Serializable {

    @ApiModelProperty(value = "设备ID")
    private String DeviceID;

    @ApiModelProperty(value = "控制类型。当Ext为1时AppCmd不起作用")
    private Integer AppCmd;

    @ApiModelProperty(value = "透明数据")
    private String TransData;

}