package com.kedacom.acl.network.ums.responsevo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/12
 */
@Data
public class DeviceInfoVo implements Serializable {

    @ApiModelProperty(value = "设备类型, 统一设备为28")
    private String devtype;

    @ApiModelProperty(value = "统一设备IP地址")
    private String devplatip;

    @ApiModelProperty(value = "统一设备端口")
    private Integer devplatport;

    @ApiModelProperty(value = "设备状态订阅IP")
    private String devnotifyaip;

    @ApiModelProperty(value = "媒体调度服务IP")
    private String mediascheduleip;

    @ApiModelProperty(value = "媒体调度服务端口")
    private Integer mediascheduleport;

    @ApiModelProperty(value = "流媒体服务IP")
    private String nmediaip;

    @ApiModelProperty(value = "流媒体服务端口")
    private Integer nmediaport;

    @ApiModelProperty(value = "录像服务端口")
    private Integer recport;

}
