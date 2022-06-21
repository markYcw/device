package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author hxj
 * @date 2022/6/21 18:41
 */
@Data
@ToString(callSuper = true)
@ApiModel(description = "查询会议录像任务交互参数")
public class QueryMeetRecTaskRequest extends BaseRequest {

    private static final String COMMAND = "recmtaskquery";

    @ApiModelProperty("登录token")
    @JSONField(name = "account_token")
    private String accountToken;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    @JSONField(name = "request_id")
    private String requestId;

    @Override
    public String name() {
        return null;
    }
}
