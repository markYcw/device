package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/22 11:07
 * @description rtsp信息，protoType为2时生效
 */
@Data
public class RtspInfo {

    @NotBlank(message = "rtsp的url不能为空")
    @ApiModelProperty(value = "rtsp的url",required = true)
    private String rtspUrl;

    @NotNull(message = "rtsp传输模式不能为空")
    @ApiModelProperty(value = "rtsp传输模式 0:tcp 1:udp",required = true)
    private Integer transMode;

}
