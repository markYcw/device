package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author hxj
 * @date 2022/6/21 16:17
 */
@Data
@ToString(callSuper = true)
@ApiModel(description = "停止会议录像业务交互参数")
public class StopMeetRecRequest extends BaseRequest {

    private static final String COMMAND = "stoprecm";

    @ApiModelProperty("登录token")
    private String account_token;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

    @ApiModelProperty("录像ID")
    private String record_id;

    @Override
    public String name() {
        return COMMAND;
    }

}
