package com.kedacom.device.mp.mcu.response;

import com.kedacom.device.mp.MpResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hxj
 * @date: 2021/8/19 13:15
 * @description 添加/删除终端中间件响应
 */
@Data
@ApiModel(description =  "添加/删除终端中间件响应")
public class McuMtResponse extends MpResponse {

    @ApiModelProperty(value = "终端ID")
    private String mtId;

}
