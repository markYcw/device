package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/5/26 14:46
 */
@Data
@ApiModel("查询实时资源URL请求")
public class QueryRealUrlRequest extends BaseRequest {

    private static final String COMMAND = "queryrealurl";

    @ApiModelProperty("协议类型：hls—hls协议、rtmp—rtmp协议、rtsp—rtspovertcp、http—通过http协议流式下载音视频资源文件")
    private String protocol;

    @ApiModelProperty("资源ID当使用RSTP协议时，device_id参数新增支持使用“视频资源ID-音频资源ID”来分别指定视频来源和音频来源")
    private String deviceId;

    @ApiModelProperty("HLS/HTTP协议的外网映射地址。**适用于hls/http协议流媒体微服务会根据此参数替换返回的url中的相应部分")
    private String natUrl;

    @ApiModelProperty("RTSP/RTMP协议的外网映射地址。**适用于rtsp/rtmp协议流媒体微服务会根据此参数替换返回的url中的相应部分")
    private String natIp;

    @ApiModelProperty("域Id")
    private Integer nmediaId;

    @Override
    public String name() {
        return COMMAND;
    }
}
