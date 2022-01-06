package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/1/6 14:20
 * @description
 */
@Data
public class RemotePointOffVo extends SvrRequestDto{

    @NotBlank(message = "远程点名称不能为空")
    @ApiModelProperty(value = "远程点名称",required = true)
    private String rpName;

    @NotBlank(message = "远程点url不能为空")
    @ApiModelProperty(value = "远程点url",required = true)
    private String url;
}
