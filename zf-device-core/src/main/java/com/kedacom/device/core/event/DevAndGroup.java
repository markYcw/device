package com.kedacom.device.core.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangxy
 * @describe
 * @date 2021/7/29
 */
@Data
public class DevAndGroup {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "分组id")
    private String groupId;

    @ApiModelProperty(value = "国标id")
    private String gbid;

}
