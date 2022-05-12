package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/4 14:51
 * @description
 */
@ApiModel(description = "编码通道信息")
@Data
public class EnChn {

    @ApiModelProperty("国标ID")
    private String gbId;

    @ApiModelProperty("通道编号")
    private Integer sn;



}
