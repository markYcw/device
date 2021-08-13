package com.kedacom.device.meetingPlatform.mcu.response;

import com.kedacom.device.meetingPlatform.MeetingResponse;
import com.kedacom.meeting.mcu.pojo.ConfInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/13 13:43
 * @description 获取会议列表中间件响应
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "获取会议列表中间件响应")
public class McuConfsResponse extends MeetingResponse {

    @ApiModelProperty(value = "会议列表")
    private List<ConfInfo> confInfo;

}
