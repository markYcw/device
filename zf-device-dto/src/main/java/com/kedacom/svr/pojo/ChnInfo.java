package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 15:40
 * @description 通道信息，添加时必填
 */
@Data
public class ChnInfo {

    @ApiModelProperty(value = "设备guid")
    private String guid;

    @NotBlank(message = "设备类型名称不能为空")
    @ApiModelProperty(value = "设备类型名称",required = true)
    private String typeName;

    @NotBlank(message = "通道名称不能为空")
    @ApiModelProperty(value = "通道名称",required = true)
    private String name;

    @ApiModelProperty(value = "通道别名")
    private String alias;

    @NotNull(message = "协议类型不能为空")
    @ApiModelProperty(value = "协议类型:0：VSIP 1：ONVIF 2：RTSP 3：MT（会议）4：SDI 5：平台通道 6：GB（国标）7：RTMP",required = true)
    private Integer protoType;

    @NotBlank(message = "设备IP不能为空")
    @ApiModelProperty(value = "设备IP",required = true)
    private String ip;

    @Valid
    @ApiModelProperty("onvif信息，protoType为1时生效")
    private OnVifInfo onvifInfo;

    @Valid
    @ApiModelProperty("rtsp信息，protoType为2时生效")
    private RtspInfo rtspInfo;

    @Valid
    @ApiModelProperty("rtmp信息，protoType为7时生效")
    private RtmpInfo rtmpInfo;

}
