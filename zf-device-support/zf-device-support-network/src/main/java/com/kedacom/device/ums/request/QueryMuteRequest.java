package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/15
 */
@Data
@ToString(callSuper = true)
public class QueryMuteRequest extends BaseRequest {

    private static final String COMMAND = "querygroupmute";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "设备Id")
    private String DeviceID;

    @Override
    public String name() {
        return COMMAND;
    }

}
