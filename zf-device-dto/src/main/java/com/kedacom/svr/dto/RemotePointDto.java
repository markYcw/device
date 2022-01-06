package com.kedacom.svr.dto;

import com.kedacom.svr.pojo.RemotePoint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/10 14:33
 * @description 启用/停止远程点
 */
@Data
public class RemotePointDto extends SvrRequestDto{

    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "0：启动远程点 1：停止远程点",required = true)
    private Integer type;

    @Valid
    @ApiModelProperty(value = "远程点",required = true)
    private RemotePoint remotePoint;

}
