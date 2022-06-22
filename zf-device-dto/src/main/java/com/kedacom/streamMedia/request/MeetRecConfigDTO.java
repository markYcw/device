package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/22 9:20
 */
@Data
@ApiModel(description = "会议录像全局配置入参")
public class MeetRecConfigDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @ApiModelProperty(value = "覆盖策略，0录像满覆盖，无告警通知; 1录像满停止，有告警通知")
    private Integer coverPolicy;

    @ApiModelProperty(value = "配额空间：单位GB")
    private Integer quotaSize;

    @ApiModelProperty(value = "告警阈值，百分之多少")
    private Integer capacityAlarmPercent;

    @ApiModelProperty(value = "告警检测间隔。默认10秒")
    private Integer capacityAlarmInterval;

}
