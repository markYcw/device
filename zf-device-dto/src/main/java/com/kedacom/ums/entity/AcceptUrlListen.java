package com.kedacom.ums.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/6/1 13:54
 */
@Data
public class AcceptUrlListen implements Serializable {

    @ApiModelProperty("主题统一为:\"zf_ums_topic_AV_DEVICE_STATUS\"")
    private String topic;

    @ApiModelProperty("接受设备状态变更通知消息的接口地址如:http://127.0.0.1:10090/demo/deviceStatus;调用方接口定义如下要求-->{@PostMapping、@RequestBody UmsSubDeviceStatusModel model}")
    private String acceptUrl;

}
