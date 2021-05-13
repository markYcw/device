package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/7
 */
@Data
@ApiModel(value = "修改统一设备信息请求参数类")
public class UmsDeviceInfoUpdateRequestDto implements Serializable {

    @NotBlank(message = "设备Id不能为空")
    @ApiModelProperty(value = "设备Id")
    private String id;

    @ApiModelProperty(value = "设备名称，必填")
    @NotNull(message = "设备名称不能为空")
    @Size(max = 125,message = "设备名称不得超过125个字")
    private String name;

    @ApiModelProperty(value = "设备类型, 统一设备为28")
    @NotNull(message = "设备类型不能为空")
    private String deviceType;

    @ApiModelProperty(value = "统一设备IP地址")
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "IP地址参数不合法")
    private String deviceIp;

    @ApiModelProperty(value = "统一设备端口")
    @Min(value = 0, message = "设备端口号参数不正确")
    @Max(value = 65536, message = "设备端口号参数不正确")
    private Integer devicePort;

    @ApiModelProperty(value = "设备状态订阅IP",notes = "已经被kafkaAddr字段取代，没用了")
    private String deviceNotifyIp;

    @ApiModelProperty(value = "设备状态订阅信息",example = "172.16.1.1:8080,172.16.1.2:9099")
    @NotNull(message = "设备状态订阅信息不能为空")
    private String kafkaAddr;

    @ApiModelProperty(value = "媒体调度服务IP")
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "IP地址参数不合法")
    private String mediaIp;

    @ApiModelProperty(value = "媒体调度服务端口")
    @Min(value = 0, message = "媒体调度端口号参数不正确")
    @Max(value = 65536, message = "媒体调度端口号参数不正确")
    private Integer mediaPort;

    @ApiModelProperty(value = "流媒体服务IP")
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "IP地址参数不合法")
    private String streamingMediaIp;

    @ApiModelProperty(value = "流媒体服务端口")
    @Min(value = 0, message = "流媒体服务端口号参数不正确")
    @Max(value = 65536, message = "流媒体服务端口号参数不正确")
    private Integer streamingMediaPort;

    @ApiModelProperty(value = "录像服务端口")
    @Min(value = 0, message = "录像服务端口号参数不正确")
    @Max(value = 65536, message = "录像服务端口号参数不正确")
    private Integer streamingMediaRecPort;

}
