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

    @ApiModelProperty("设备编号")
    private String puId;

    @ApiModelProperty("视频源编号")
    private Integer sn;

    @ApiModelProperty("视频源名称")
    private String name;

    @ApiModelProperty("是否启用 0:不启用，1：启用")
    private Integer enable = 0;

    @ApiModelProperty("0:离线，1:在线")
    private Integer online = 0;

    @ApiModelProperty("是否平台录像。0: 未知 1：空闲 2：录像 3：尝试中 4：停止中")
    private Integer platRecord = 1;

    @ApiModelProperty("是否前端录像。0: 未知 1：空闲 2：录像 3：尝试中 4：停止中")
    private Integer puRecord = 1;


}
