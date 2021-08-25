package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 13:46
 */
@Data
@ApiModel(description =  "开始推送媒体流入参")
public class StartPushUrlDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "设备国标id不能为空")
    @ApiModelProperty(value = "设备国标id", required = true)
    private String deviceID;

    @NotBlank(message = "协议类型不能为空")
    @ApiModelProperty(value = "协议类型:rtmp—rtmp协议;rtsp—rtsp over tcp;http—http协议", required = true)
    private String protocol;

    @NotBlank(message = "推送的目标URL不能为空")
    @ApiModelProperty(value = "流媒体微服务推送媒体资源到目的url,支持主动推送的码流源协议类型", required = true)
    private String url;

}
