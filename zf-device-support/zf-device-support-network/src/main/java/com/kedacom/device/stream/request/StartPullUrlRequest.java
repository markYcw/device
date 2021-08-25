package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 14:57
 */
@ToString(callSuper = true)
@Data
@ApiModel(description = "开始推送媒体流交互参数")
public class StartPullUrlRequest extends BaseRequest {

    private static final String COMMAND = "startpullurl";

    @ApiModelProperty(value = "设备国标id", required = true)
    @JSONField(name = "DeviceID")
    private String deviceID;

    @ApiModelProperty("协议类型:rtmp—rtmp协议;rtsp—rtsp over tcp;http—http协议")
    private String protocol;

    @ApiModelProperty("流媒体微服务从源url拉取媒体资源,支持主动拉取源地址码流的协议类型:1、在protocol为rtsp时生效、支持主动从url拉取RTSP协议码流源或是通过http协议获取MP4文件作为源。\n" +
            "2、在protocol为rtmp时生效、支持主动从url拉取RTMP协议码流源。")
    private String url;

    @Override
    public String name() {
        return COMMAND;
    }

}
