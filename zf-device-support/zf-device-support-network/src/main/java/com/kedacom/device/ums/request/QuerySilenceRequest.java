package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/15
 */
@Data
@ToString(callSuper = true)
public class QuerySilenceRequest extends BaseRequest {

    private static final String COMMAND = "querygroupsilence";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "设备Id")
    private String DeviceID;

    @Override
    public String name() {
        return COMMAND;
    }

}
