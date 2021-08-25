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
@ApiModel(description =  "创建调度组交互参数类")
public class UmsScheduleGroupCreateRequest implements Serializable {

    @ApiModelProperty("调度组成员列表")
    private List<UmsScheduleGroupMembersRequestDto> Members;

}
