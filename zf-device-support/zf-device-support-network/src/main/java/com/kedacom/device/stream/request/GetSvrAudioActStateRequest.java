package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/8 13:09
 * @description
 */
@ToString(callSuper = true)
@Data
public class GetSvrAudioActStateRequest extends BaseRequest {

    private static final String COMMAND = "getsvraudioactstate";

    @ApiModelProperty("设备ID")
    @JSONField(name = "DeviceID")
    private String deviceID;

    @Override
    public String name() {
        return COMMAND;
    }
}
