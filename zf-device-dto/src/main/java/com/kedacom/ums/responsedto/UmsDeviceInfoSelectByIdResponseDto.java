package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/10
 */
@Data
@ApiModel(description =  "根据id查询统一设备响应参数类")
public class UmsDeviceInfoSelectByIdResponseDto implements Serializable {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "设备名称，必填")
    private String name;

    @ApiModelProperty(value = "设备类型, 统一设备为28")
    private String deviceType;

    @ApiModelProperty(value = "统一设备IP地址")
    private String deviceIp;

    @ApiModelProperty(value = "统一设备端口")
    private Integer devicePort;

    @ApiModelProperty(value = "设备状态订阅IP")
    private String deviceNotifyIp;

    @ApiModelProperty(value = "媒体调度服务IP")
    private String mediaIp;

    @ApiModelProperty(value = "媒体调度服务端口")
    private Integer mediaPort;

    @ApiModelProperty(value = "分组Id")
    private String groupId;

    @ApiModelProperty(value = "流媒体服务IP")
    private String streamingMediaIp;

    @ApiModelProperty(value = "流媒体服务端口")
    private Integer streamingMediaPort;

    @ApiModelProperty(value = "录像服务端口")
    private Integer streamingMediaRecPort;

    @ApiModelProperty(value = "最近一次同步第三方服务子设备时间")
    private String lastSyncThirdDeviceTime;

    @ApiModelProperty(value = "拼控服务账号")
    private String mspAccount;

    @ApiModelProperty(value = "拼控服务密码")
    private String mspPassword;

}
