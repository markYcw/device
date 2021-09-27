package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hxj
 * @date: 2021/8/17 10:27
 * @description 开启会议模板入参
 */
@Data
@ApiModel(description =  "开启会议模板入参")
public class McuConfTemplateDTO extends McuRequestDTO {

    @NotBlank(message = "会议模板ID不能为空")
    @ApiModelProperty(value = "会议模板ID",required = true)
    private String templateId;

}
