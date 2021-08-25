package com.kedacom.device.mp.mcu.response;

import com.kedacom.device.mp.MpResponse;
import com.kedacom.mp.mcu.pojo.ListConfInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/13 13:43
 * @description 获取会议模板列表中间件响应
 */
@Data
@ToString(callSuper = true)
@ApiModel(description =  "获取会议模板列表中间件响应")
public class McuTemplatesResponse extends MpResponse {

    @ApiModelProperty(value = "结果列表")
    private List<ListConfInfo> confInfo;

}
