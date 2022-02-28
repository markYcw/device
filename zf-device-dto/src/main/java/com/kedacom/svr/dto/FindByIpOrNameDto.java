package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/2/28 16:43
 * @description
 */
@AllArgsConstructor
@Data
public class FindByIpOrNameDto {

    @ApiModelProperty("设备IP")
    private String ip;

    @ApiModelProperty("设备名称")
    private String name;

}
