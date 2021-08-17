package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:08
 * @description 终端双流控制向中间件请求参数
 */
@Data
@ApiModel(value = "终端双流控制向中间件请求参数")
public class McuDualRequest implements Serializable {

    @NotBlank(message = "会议号码不能为空")
   @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @NotBlank(message = "终端ip或者e164号不能为空")
    @ApiModelProperty(value = "终端ip或者e164号", required = true)
    private String mtId;

}
