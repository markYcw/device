package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 15:02
 */
@Data
@ApiModel(description =  "停止拉取媒体流交互参数")
public class StopPullUrlRequest extends BaseRequest {

    private static final String COMMAND = "stoppullurl";

    @ApiModelProperty(value = "设备国标id", required = true)
    @JSONField(name = "DeviceID")
    private String deviceID;

    @ApiModelProperty("资源ID;庭审场景，请使用LCXFF节点")
    private Integer sessionID;

    @Override
    public String name() {
        return COMMAND;
    }

}
