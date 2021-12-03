package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 11:01
 * @description
 */
@Data
public class StartDualRequestVo extends SvrRequestDto{

    @ApiModelProperty("0：开启 1：关闭")
    private Integer type;

    @ApiModelProperty("远程点通道号")
    private Integer chnId;

}
