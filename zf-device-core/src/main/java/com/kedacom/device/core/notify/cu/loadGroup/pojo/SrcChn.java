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
@ApiModel(description = "视频源信息")
@Data
public class SrcChn {

    @ApiModelProperty("视频源编号")
    private String sn;

    @ApiModelProperty("视频源名称")
    private String name;


}
