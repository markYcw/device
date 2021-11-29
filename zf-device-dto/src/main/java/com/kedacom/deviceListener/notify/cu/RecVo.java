package com.kedacom.deviceListener.notify.cu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/29 10:28
 * @description
 */
@ApiModel(description = "视频源信息")
@Data
public class RecVo {
    @ApiModelProperty("视频源通道号")
    private Integer sn;

    @ApiModelProperty("平台录像状态 0: 位置 1：空闲 2：录像 3：尝试中 4：停止中")
    private Integer plat;

    @ApiModelProperty("前端录像状态 0: 位置 1：空闲 2：录像 3：尝试中 4：停止中")
    private Integer pu;
}
