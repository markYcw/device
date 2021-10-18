package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.pojo.CdConfInfo;
import com.kedacom.mp.mcu.pojo.TemplateInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @date: 2021/9/24 15:29
 * @description 创建/删除会议模板
 */
@Data
@ApiModel(description =  "创建/删除会议模板入参")
public class ConfTemplateDTO extends McuRequestDTO {

    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "0：0：创建会议模板 1：1：删除会议模板",required = true)
    private Integer type;

    @ApiModelProperty(value = "会议模板ID，type 是1时必填")
    private String templateId;

    @ApiModelProperty(value = "会议模板信息，创建时必填")
    @Valid
    private TemplateInfo templateInfo;

}
