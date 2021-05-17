package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.ums.requestdto.UmsScheduleGroupMembersRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/15
 */
@Data
@ToString(callSuper = true)
public class AddMembersRequest extends BaseRequest {

    private static final String COMMAND = "addgroupmember";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "调度组成员设备信息")
    private List<UmsScheduleGroupMembersRequestDto> Members;

    @Override
    public String name() {
        return COMMAND;
    }
}
