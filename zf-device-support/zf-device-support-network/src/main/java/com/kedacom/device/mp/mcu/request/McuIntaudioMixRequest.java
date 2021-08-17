package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:18
 * @description 智能混音向中间件请求参数
 */
@Data
@ApiModel(value = "智能混音向中间件请求参数")
public class McuIntaudioMixRequest implements Serializable {

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码",required = true)
    private String confId;

}
