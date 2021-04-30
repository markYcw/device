package com.kedacom.streamMedia.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 09:38
 */
@Data
public class AudioMixer implements Serializable {

    @ApiModelProperty("参与混音方终端ID")
    private String deviceID;

    @ApiModelProperty(value = "域ID。*在跨域访问场景必填" +
            "deviceID不为空时选填，表示交换节点（resourceID）所在的流媒体微服务和目标设备不在同一网络环境中，且需要通过目标设备所在域的指定流媒体微服务（nmedia_id）才能正确获取目标设备的音视频码流。" +
            "若nmedia_id=0或不填，表示交换节点所在的流媒体微服务和目标视频源在同一网络中，通过本流媒体微服务上交换节点即可获取到目标设备的音视频码流")
    private Integer nmediaId;

}
