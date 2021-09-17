package com.kedacom.svr.stragegy.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 16:25
 * @description 设备信息
 */
@Data
public class DeviceInfo {

    @ApiModelProperty("设备guid")
    private String guid;

    @ApiModelProperty("设备名称")
    private String name;

    @ApiModelProperty("设备类型名称")
    private String typeName;

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("设备类型 0：无效设备 1：编码器 2：解码器 3：增加支持平台设备,针对版本Beta2 4：多媒体主机,包括SVR29ES 和 SVR27ES 5：SDI编码卡 6：2726-VIN0 VIN1")
    private Integer devType;

    @ApiModelProperty("设备厂商 0：kedacom 1：外厂商（目前无用）")
    private String devManu;

    @ApiModelProperty("协议类型 0：VSIP 1：ONVIF 2：RTSP 3：MT（会议）4：SDI 5：平台通道 6：GB（国标）")
    private Integer protoType;

    @ApiModelProperty("onvif信息，protoType为1时生效")
    private OnvifInfo onvifInfo;

}
