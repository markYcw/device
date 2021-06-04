package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/4
 */
@Data
@ToString(callSuper = true)
public class ScheduleGroupStatusRequest extends BaseRequest {

    private static final String COMMAND = "getgroupstatus";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @Override
    public String name() {
        return COMMAND;
    }

}
