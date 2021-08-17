package com.kedacom.device.mp.mcu.response;

import com.kedacom.device.mp.MpResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hxj
 * @date: 2021/8/17 10:24
 * @description 创建/删除会议中间件响应
 */
@Data
@ApiModel(value = "创建/删除会议中间件响应")
public class McuConfResponse extends MpResponse {

    @ApiModelProperty(value = "会议号码，创建时返回")
    private String confId;

}
