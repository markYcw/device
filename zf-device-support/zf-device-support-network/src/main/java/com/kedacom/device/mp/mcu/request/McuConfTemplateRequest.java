package com.kedacom.device.mp.mcu.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 10:27
 * @description 开启会议模板向中间件请求参数
 */
@Data
@ApiModel(description =  "开启会议模板向中间件请求参数")
public class McuConfTemplateRequest implements Serializable {

    @ApiModelProperty(value = "会议模板ID",required = true)
    private String templateId;

}
