package com.kedacom.device.core.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/4
 */
@Data
public class MediaResourceNode implements Serializable {

    @ApiModelProperty(value = "设备视频发送资源ID")
    private String VideoSendResourceID;

    @ApiModelProperty(value = "设备视频接收资源ID")
    private String VideoRecvResourceID;

    @ApiModelProperty(value = "设备音频发送资源ID")
    private String AudioSendResourceID;

    @ApiModelProperty(value = "设备音频接收资源ID")
    private String AudioRecvResourceID;

}
