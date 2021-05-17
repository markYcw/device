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
 * @date 2021/5/14
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Data
public class CancelBroadcastRequest extends BaseRequest {

    private static final String COMMAND = "cancelbroadcast";

    @ApiModelProperty(value = "调度组ID")
    private String GroupID;

    @Override
    public String name() {
        return COMMAND;
    }
}
