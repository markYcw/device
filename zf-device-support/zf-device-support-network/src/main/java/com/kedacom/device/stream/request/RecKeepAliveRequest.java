package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hxj
 * @date: 2021/8/18 15:03
 * @description 录像任务保活中间件入参
 */
@Data
@ApiModel("录像任务保活中间件入参")
public class RecKeepAliveRequest extends BaseRequest {

    private static final String COMMAND = "reckeepalive";

    @ApiModelProperty("登录token")
    @JSONField(name = "account_token")
    private String accountToken;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    @JSONField(name = "request_id")
    private String requestId;

    @ApiModelProperty("录像ID")
    @JSONField(name = "record_id")
    private String recordId;

    @Override
    public String name() {
        return COMMAND;
    }

}
