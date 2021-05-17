package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.ums.requestdto.UmsScheduleGroupMembersSetSilenceRequestDto;
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
public class SetSilenceRequest extends BaseRequest {

    private static final String COMMAND = "setgroupsilence";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "调度组成员设备设置静音")
    private List<UmsScheduleGroupMembersSetSilenceRequestDto> Members;

    @Override
    public String name() {
        return COMMAND;
    }

}
