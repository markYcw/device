package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(description =  "创建调度组成员设备信息响应参数类")
public class UmsScheduleGroupMembersResponseDto implements Serializable {

    @ApiModelProperty(value = "成员设备Id")
    private String deviceId;

}
