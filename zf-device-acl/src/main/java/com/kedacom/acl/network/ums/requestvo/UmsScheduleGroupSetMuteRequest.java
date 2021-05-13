package com.kedacom.acl.network.ums.requestvo;

import com.kedacom.ums.requestdto.UmsScheduleGroupMembersSetMuteRequestDto;
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
@ApiModel(value = "设置调度组哑音请求参数类")
public class UmsScheduleGroupSetMuteRequest implements Serializable {

    @ApiModelProperty(value = "平台id")
    private String umsId;

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "调度组成员设备设置哑音")
    private List<UmsScheduleGroupMembersSetMuteRequestDto> Members;

}
