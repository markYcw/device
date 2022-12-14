package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @date: 2021/8/16 11:01
 * @description 会议模板列表
 */
@Data
@ApiModel(description =  "会议模板列表")
public class ListConfTemplateInfo implements Serializable {

    @ApiModelProperty(value = "会议模板ID")
    private String templateId;

    @ApiModelProperty(value = "名称")
    private String name;

}
