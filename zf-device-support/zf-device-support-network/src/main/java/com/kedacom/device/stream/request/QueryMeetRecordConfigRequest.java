package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author hxj
 * @date 2022/6/22 10:08
 */
@Data
@ToString(callSuper = true)
public class QueryMeetRecordConfigRequest extends BaseRequest {

    private static final String COMMAND = "recmconfigquery";

    @ApiModelProperty("登录token")
    @JSONField(name = "account_token")
    private String accountToken;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    @JSONField(name = "request_id")
    private String requestId;

    @Override
    public String name() {
        return COMMAND;
    }
}
