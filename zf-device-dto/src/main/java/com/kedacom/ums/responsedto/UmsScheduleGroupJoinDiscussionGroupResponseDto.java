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
@ApiModel(value = "加入讨论组响应参数类")
public class UmsScheduleGroupJoinDiscussionGroupResponseDto implements Serializable {

    @ApiModelProperty(value = "成员设备Id")
    private String deviceId;

}
