package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/26 13:21
 * @description
 */
@Data
@ApiModel(description =  "获取会议模板信息")
public class GetConfTemplateDTO extends McuRequestDTO {

    @NotBlank(message = "会议模板ID，不能为空")
    @ApiModelProperty(value = "会议模板ID")
    private String templateId;


}
