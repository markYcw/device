package com.kedacom.mp.mcu.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/24 16:00
 * @description
 */
@Data
@ApiModel(description = "创建/删除会议模板响应")
public class ConfTemplateVo implements Serializable {

    @ApiModelProperty(value = "会议模板ID，创建时返回")
    private String templateId;

}
