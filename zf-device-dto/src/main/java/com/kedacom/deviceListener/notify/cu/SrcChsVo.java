package com.kedacom.deviceListener.notify.cu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/29 10:13
 * @description
 */
@Data
@ApiModel(description = "视频源信息")
public class SrcChsVo {

    @ApiModelProperty("视频源编号")
    private Integer sn;

    @ApiModelProperty("0:不启用，1：启用")
    private Integer enable;

    @ApiModelProperty("0:离线，1:在线")
    private Integer online;
}
