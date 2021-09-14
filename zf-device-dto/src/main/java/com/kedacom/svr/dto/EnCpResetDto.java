package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 10:27
 * @description
 */
@Data
public class EnCpResetDto extends SvrRequestDto{

    @ApiModelProperty("通道ID")
    private Integer chnId;

}
