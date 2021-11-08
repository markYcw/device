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
public class SrcChns {

    @ApiModelProperty("视频源编号")
    private String sn;

    @ApiModelProperty("0:不启用，1：启用")
    private Integer enable;

    @ApiModelProperty("0:离线，1:在线")
    private Integer online;

}
