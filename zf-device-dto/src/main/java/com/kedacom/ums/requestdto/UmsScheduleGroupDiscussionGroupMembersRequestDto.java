package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "加入讨论组成员信息")
public class UmsScheduleGroupDiscussionGroupMembersRequestDto implements Serializable {

    @NotBlank(message = "成员设备类型不能为空")
    @ApiModelProperty(value = "成员设备类型")
    private String deviceType;

    @NotBlank(message = "成员设备Id不能为空")
    @ApiModelProperty(value = "成员设备Id")
    private String deviceId;

}