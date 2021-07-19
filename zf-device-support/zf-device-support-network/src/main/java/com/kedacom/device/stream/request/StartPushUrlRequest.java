package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 14:09
 */
@ToString(callSuper = true)
@Data
@ApiModel("开始推送媒体流交互参数")
public class StartPushUrlRequest extends BaseRequest {

    private static final String COMMAND = "startpushurl";

    @ApiModelProperty(value = "设备国标id", required = true)
    @JSONField(name = "DeviceID")
    private String deviceID;

    @ApiModelProperty("协议类型:rtmp—rtmp协议")
    private String protocol;

    @ApiModelProperty("流媒体微服务推送媒体资源到目的url,支持主动推送的码流源协议类型,1、在protocol为rtmp时生效")
    private String url;

    @Override
    public String name() {
        return COMMAND;
    }

}
