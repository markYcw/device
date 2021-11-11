package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@Data
@ApiModel(description = "发布rtsp源请求参数类")
public class StreamRequestVo implements Serializable {

    @ApiModelProperty("用户填写的唯一标识默认填1")
    private String id;

    @ApiModelProperty("发布类型, 1：rtsp 2: rtmp 3: webrtc 4: httpflv(http) 5: httpflv-ws(websocket)")
    private Integer type;

    @ApiModelProperty("rtsp")
    private String rtsp;

    @ApiModelProperty("bshare默认填1")
    private Integer bshare;

    @ApiModelProperty("组信息默认填1")
    private String group;

}
