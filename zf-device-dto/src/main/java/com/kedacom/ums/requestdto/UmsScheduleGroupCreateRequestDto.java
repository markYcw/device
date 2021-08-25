package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(description =  "创建调度组请求参数类")
public class UmsScheduleGroupCreateRequestDto implements Serializable {

    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotEmpty(message = "调度组成员列表不能为空")
    @ApiModelProperty("调度组成员列表")
    private List<UmsScheduleGroupMembersRequestDto> Members;

}
