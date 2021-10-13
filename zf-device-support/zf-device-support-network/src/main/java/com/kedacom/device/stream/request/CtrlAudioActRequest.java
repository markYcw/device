package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 14:13
 * @description
 */
@ToString(callSuper = true)
@Data
public class CtrlAudioActRequest extends BaseRequest {

    private static final String COMMAND = "ctrlaudioact";

    @ApiModelProperty("设备ID")
    @JSONField(name = "DeviceID")
    private String deviceID;

    @ApiModelProperty(value = "控制类型 0：开始 1：停止", required = true)
    private Integer type;

    @Override
    public String name() {
        return COMMAND;
    }

}
