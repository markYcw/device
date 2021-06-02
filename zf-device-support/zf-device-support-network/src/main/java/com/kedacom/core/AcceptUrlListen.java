package com.kedacom.core;

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

    @ApiModelProperty("接受消息URL(即接受通知消息的接口地址):例子-->url:http://127.0.0.1:10090/demo/deviceStatus    @RequestBody:UmsSubDeviceStatusModel model")
    private String acceptUrl;

}
