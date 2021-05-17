package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.ums.requestdto.UmsScheduleGroupDiscussionGroupMembersRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class JoinDiscussionGroupRequest extends BaseRequest {

    private static final String COMMAND = "joindiscussion";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "加入讨论组成员信息")
    private List<UmsScheduleGroupDiscussionGroupMembersRequestDto> Members;

    @Override
    public String name() {
        return COMMAND;
    }

}
