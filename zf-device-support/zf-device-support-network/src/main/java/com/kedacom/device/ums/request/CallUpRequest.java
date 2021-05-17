package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.ums.requestdto.UmsScheduleGroupSubDeviceCallMembersRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/14
 */
@Data
@ToString(callSuper = true)
public class CallUpRequest extends BaseRequest {

    private static final String COMMAND = "callmember";

    @ApiModelProperty(value = "调度组ID")
    private String GroupID;

    private List<UmsScheduleGroupSubDeviceCallMembersRequestDto> CallMembers;

    @Override
    public String name() {
        return COMMAND;
    }
}
