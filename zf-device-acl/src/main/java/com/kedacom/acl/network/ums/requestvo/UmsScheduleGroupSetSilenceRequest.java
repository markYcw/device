package com.kedacom.acl.network.ums.requestvo;

import com.kedacom.ums.requestdto.UmsScheduleGroupMembersSetSilenceRequestDto;
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
@ApiModel(description =  "设置调度组静音请求参数类")
public class UmsScheduleGroupSetSilenceRequest implements Serializable {

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "调度组成员设备设置静音")
    private List<UmsScheduleGroupMembersSetSilenceRequestDto> Members;

}
