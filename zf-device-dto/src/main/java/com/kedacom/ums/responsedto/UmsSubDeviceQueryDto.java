package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Data
@ApiModel(value = "查询子设备部分信息响应参数类")
public class UmsSubDeviceQueryDto implements Serializable {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "设备名")
    private String name;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "分组Id")
    private String groupId;

    @ApiModelProperty(value = "设备状态（0：在线 1：离线 2：故障）")
    private Integer status;

    @ApiModelProperty(value = "设备模式 0正常 1同步中 2已丢失")
    private Integer deviceMod;

}
