package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hxj
 * @date 2022/6/21 19:34
 */
@Data
@ApiModel(description = "删除会议录像入参")
public class DeleteMeetRecRequest extends BaseRequest {

    private static final String COMMAND = "deleterecm";

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @ApiModelProperty("登录token")
    @JSONField(name = "account_token")
    private String accountToken;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    @JSONField(name = "request_id")
    private String requestId;

    @ApiModelProperty(value = "录像ID")
    @JSONField(name = "record_id")
    private String recordId;

    @ApiModelProperty(value = "录像开始时间，如果没有录像开始结束时间将删除该录像ID的所有录像")
    private Date startTime;

    @ApiModelProperty(value = "录像结束时间")
    private Date endTime;

    @Override
    public String name() {
        return COMMAND;
    }
}
