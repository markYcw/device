package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.streamMedia.info.CapacityAlarmUrl;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author hxj
 * @date 2022/6/22 10:14
 */
@Data
@ToString(callSuper = true)
public class QueryMeetRecordConfigResponse extends BaseResponse {

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String requestId;

    @ApiModelProperty(value = "覆盖策略，0录像满覆盖，无告警通知; 1录像满停止，有告警通知")
    private Integer coverPolicy;

    @ApiModelProperty(value = "告警阈值，百分之多少")
    private Integer capacityAlarmPercent;

    @ApiModelProperty(value = "告警url数组")
    private List<CapacityAlarmUrl> capacityAlarmUrlList;

}
