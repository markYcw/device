package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/26 14:56
 */
@Data
@ApiModel(description = "查询历史资源URL入参")
public class QueryHistoryUrlDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @ApiModelProperty("协议类型：hls—hls协议、rtmp—rtmp协议、rtsp—rtspovertcp、http—通过http协议流式下载音视频资源文件")
    @NotBlank(message = "协议类型不能为空")
    private String protocol;

    @ApiModelProperty("国标id---当使用RSTP协议时，device_id参数新增支持使用“视频资源ID-音频资源ID”来分别指定视频来源和音频来源")
    @NotBlank(message = "资源ID不能为空")
    private String device_id;

    @ApiModelProperty("HLS/HTTP协议的外网映射地址。**适用于hls/http协议流媒体微服务会根据此参数替换返回的url中的相应部分")
    private String nat_url;

    @ApiModelProperty("RTSP/RTMP协议的外网映射地址。**适用于rtsp/rtmp协议流媒体微服务会根据此参数替换返回的url中的相应部分")
    private String nat_ip;

    @ApiModelProperty("媒体源加载速度：1（缺省）、2、4、8")
    private Integer play_speed;

    @ApiModelProperty("查询录像的开始时间，日期格式为:YYYY-MM-DDThh:mm:ss，如：2021-04-16T07:00:00")
    private String start_time;

    @ApiModelProperty("查询录像的结束时间，日期格式为:YYYY-MM-DDThh:mm:ss，如：2021-04-16T07:00:00")
    private String end_time;

    @ApiModelProperty("域Id")
    private Integer nmedia_id;

}
