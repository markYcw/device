package com.kedacom.device.mp.mcu.response;

import com.kedacom.device.mp.MpResponse;
import com.kedacom.mp.mcu.pojo.ConfInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hxj
 * @date: 2021/8/14 15:12
 * @description 获取会议信息中间件响应
 */
@Data
@ApiModel(value = "获取会议信息中间件响应")
public class McuConfInfoResponse extends MpResponse {

    @ApiModelProperty(value = "会议信息")
    private ConfInfo confInfo;

}
