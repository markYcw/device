package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 15:34
 * @description dvd仓门控制
 */
@Data
public class DvdDoorDto extends SvrRequestDto{

    @ApiModelProperty("0：开仓 1：关仓 2：一键校验 3：光盘封口")
    private Integer type;

    @ApiModelProperty("仓门ID")
    private Integer dvdId;


}
