package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author hxj
 * @date 2022/6/22 9:24
 */
@Data
@ToString(callSuper = true)
public class MeetRecordConfigRequest extends BaseRequest {

    private static final String COMMAND = "recmconfig";

    @ApiModelProperty("登录token")
    @JSONField(name = "account_token")
    private String accountToken;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    @JSONField(name = "request_id")
    private String requestId;

    @ApiModelProperty(value = "覆盖策略，0录像满覆盖，无告警通知; 1录像满停止，有告警通知")
    @JSONField(name = "cover_policy")
    private Integer coverPolicy;

    @ApiModelProperty(value = "配额空间：单位GB")
    @JSONField(name = "quota_size")
    private Integer quotaSize;

    @ApiModelProperty(value = "告警阈值，百分之多少")
    @JSONField(name = "capacity_alarm_percent")
    private Integer capacityAlarmPercent;

    @ApiModelProperty(value = "告警检测间隔。默认10秒")
    @JSONField(name = "capacity_alarm_interval")
    private Integer capacityAlarmInterval;

    @Override
    public String name() {
        return COMMAND;
    }
}
