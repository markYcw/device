package com.kedacom.device.meetingPlatform.mcu.response;

import com.kedacom.device.meetingPlatform.MpResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Author hxj
 * @Date: 2021/8/12 14:32
 * @Description 会议平台登录中间件返回
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "会议平台登录中间件返回")
public class McuLoginResponse extends MpResponse {

    @ApiModelProperty(value = "登录成功后会话id")
    private String ssid;

}
