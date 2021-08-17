package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 10:27
 * @description 开启会议模板向中间件请求参数
 */
@Data
@ApiModel(value = "开启会议模板向中间件请求参数")
public class McuConfTemplateRequest implements Serializable {

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码",required = true)
    private String confId;

}
