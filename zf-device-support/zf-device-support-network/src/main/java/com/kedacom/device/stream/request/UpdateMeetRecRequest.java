package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author hxj
 * @date 2022/3/29 14:35
 */
@Data
@ToString(callSuper = true)
@ApiModel(description = "更新会议录像业务交互参数")
public class UpdateMeetRecRequest extends BaseRequest {

    private static final String COMMAND = "updaterecm";

    @ApiModelProperty("登录token")
    private String account_token;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

    @ApiModelProperty("录像ID")
    private String record_id;

    @ApiModelProperty("设备ID")
    private String device_id;

    @Override
    public String name() {
        return COMMAND;
    }
}
