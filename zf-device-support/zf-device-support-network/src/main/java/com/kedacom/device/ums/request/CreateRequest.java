package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.ums.requestdto.UmsScheduleGroupMembersRequestDto;
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
public class CreateRequest extends BaseRequest {

    private static final String COMMAND = "creategroup";

    @ApiModelProperty("调度组成员列表")
    private List<UmsScheduleGroupMembersRequestDto> Members;

    @Override
    public String name() {
        return COMMAND;
    }

}
