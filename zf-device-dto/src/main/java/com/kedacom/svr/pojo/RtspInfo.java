package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/22 11:07
 * @description rtsp信息，protoType为2时生效
 */
@Data
public class RtspInfo {

    @ApiModelProperty("rtsp的url")
    private String rtspUrl;

    @ApiModelProperty("rtsp传输模式 0:tcp 1:udp ")
    private Integer transMode;

}
