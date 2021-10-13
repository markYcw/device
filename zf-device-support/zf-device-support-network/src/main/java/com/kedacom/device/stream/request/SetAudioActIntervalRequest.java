package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 14:44
 * @description
 */
@ToString(callSuper = true)
@Data
public class SetAudioActIntervalRequest extends BaseRequest {

    private static final String COMMAND = "setaudioactinterval";

    @ApiModelProperty("设备ID")
    @JSONField(name = "DeviceID")
    private String deviceID;

    @ApiModelProperty(value = "上报间隔，单位ms 范围：100-60000 eg:100", required = true)
    private Integer interval;

    @Override
    public String name() {
        return COMMAND;
    }
}
