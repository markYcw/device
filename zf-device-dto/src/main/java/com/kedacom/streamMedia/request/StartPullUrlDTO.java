package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 14:51
 */
@Data
@ApiModel(value = "开始拉取实时网络媒体流入参")
public class StartPullUrlDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "设备国标id不能为空")
    @ApiModelProperty(value = "设备国标id", required = true)
    private String deviceID;

    @NotBlank(message = "协议类型不能为空")
    @ApiModelProperty("协议类型:rtmp—rtmp协议;rtsp—rtsp over tcp;http—http协议")
    private String protocol;

    @NotBlank(message = "拉取的目标URL不能为空")
    @ApiModelProperty("流媒体微服务从源url拉取媒体资源,支持主动拉取源地址码流的协议类型:1、在protocol为rtsp时生效、支持主动从url拉取RTSP协议码流源或是通过http协议获取MP4文件作为源。\n" +
            "2、在protocol为rtmp时生效、支持主动从url拉取RTMP协议码流源。")
    private String url;

}
