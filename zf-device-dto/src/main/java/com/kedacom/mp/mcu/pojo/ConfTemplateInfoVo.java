package com.kedacom.mp.mcu.pojo;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @date: 2021/9/24 15:29
 * @description 获取会议模板信息响应
 */
@Data
@ApiModel(description =  "获取会议模板信息响应")
public class ConfTemplateInfoVo extends McuRequestDTO {

    @ApiModelProperty(value = "会议模板ID，type 是1时必填")
    private Integer templateId;

    @ApiModelProperty(value = "会议模板信息，创建时必填")
    private TemplateInfo templateInfo;

}
