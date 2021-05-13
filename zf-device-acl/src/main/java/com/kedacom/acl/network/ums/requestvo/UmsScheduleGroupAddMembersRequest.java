package com.kedacom.acl.network.ums.requestvo;

import com.kedacom.ums.requestdto.UmsScheduleGroupMembersRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "添加调度组成员设备请求参数类")
public class UmsScheduleGroupAddMembersRequest implements Serializable {

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "调度组成员设备信息")
    private List<UmsScheduleGroupMembersRequestDto> Members;

}
