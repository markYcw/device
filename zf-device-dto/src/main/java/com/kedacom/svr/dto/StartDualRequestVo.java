package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 11:01
 * @description
 */
@Data
public class StartDualRequestVo extends SvrRequestDto{

    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "0：开启 1：关闭",required = true)
    private Integer type;

    @NotNull(message = "远程点名称不能为空")
    @ApiModelProperty(value = "远程点名称",required = true)
    private Integer name;

}
