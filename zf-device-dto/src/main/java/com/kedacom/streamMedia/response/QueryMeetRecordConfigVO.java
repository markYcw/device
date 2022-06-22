package com.kedacom.streamMedia.response;

import com.kedacom.streamMedia.info.CapacityAlarmUrl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date 2022/6/22 9:34
 */
@Data
@ApiModel(description = "查询会议录像全局配置应答")
public class QueryMeetRecordConfigVO implements Serializable {

    @ApiModelProperty(value = "覆盖策略，0录像满覆盖，无告警通知; 1录像满停止，有告警通知")
    private Integer coverPolicy;

    @ApiModelProperty(value = "告警阈值，百分之多少")
    private Integer capacityAlarmPercent;

    @ApiModelProperty(value = "告警url数组")
    private List<CapacityAlarmUrl> capacityAlarmUrlList;

}
