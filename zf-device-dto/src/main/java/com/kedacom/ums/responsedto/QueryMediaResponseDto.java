package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Data
@ApiModel(value = "DeviceID为设备国标id")
public class QueryMediaResponseDto implements Serializable {

    @ApiModelProperty(value = "设备类型")
    private String DeviceType;

    @ApiModelProperty(value = "设备国标ID")
    private String DeviceID;

    @ApiModelProperty(value = "当前成员的媒体源信息")
    private MediaVo MediaSource;

}
