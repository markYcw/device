package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 10:28
 * @description 获取与会成员入参
 */
@Data
@ApiModel(value = "获取与会成员向中间件请求参数")
public class McuMtMembersRequest implements Serializable {

    @ApiModelProperty(value = "会议号码",required = true)
    private String confId;

}
