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
@ApiModel(description =  "加入讨论组请求参数类")
public class UmsScheduleGroupJoinDiscussionGroupRequestDto implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "调度组Id不能为空")
    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @NotBlank(message = "加入讨论组成员信息参数不能为空")
    @ApiModelProperty(value = "加入讨论组成员信息")
    private List<UmsScheduleGroupDiscussionGroupMembersRequestDto> Members;

}
