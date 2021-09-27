package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.ListConfTemplateInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycw
 * @date: 2021/8/12 19:24
 * @description 获取会议列表响应
 */
@Data
@ApiModel(description =  "获取会议模板列表响应")
public class McuConfTemplateVO implements Serializable {

    @ApiModelProperty(value = "总数")
    private Integer total;

    @ApiModelProperty(value = "会议模板列表")
    private List<ListConfTemplateInfo> templateInfo;

}
