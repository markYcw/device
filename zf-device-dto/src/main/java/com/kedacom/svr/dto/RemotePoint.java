package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/2/15 13:46
 * @description
 */
@Data
public class RemotePoint {

    @NotBlank(message = "远程点名称不能为空")
    @ApiModelProperty(value = "远程点名称",required = true)
    private String name;

    @NotBlank(message = "远程点url不能为空")
    @ApiModelProperty(value = "远程点url",required = true)
    private String url;

}
