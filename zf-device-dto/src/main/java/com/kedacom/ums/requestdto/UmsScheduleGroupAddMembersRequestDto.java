package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(description =  "添加调度组成员设备请求参数类")
public class UmsScheduleGroupAddMembersRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "umsId - 平台Id")
    private String umsId;

    @NotBlank(message = "调度组Id不能为空")
    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @NotBlank(message = "调度组成员设备信息不能为空")
    @ApiModelProperty(value = "调度组成员设备信息")
    private List<UmsScheduleGroupMembersRequestDto> Members;

}
