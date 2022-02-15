package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 16:01
 * @description 查询录像
 */
@Data
public class RemoteDevDto extends SvrRequestDto{

    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "0：增加远程点 1：删除远程点",required = true)
    private Integer type;

    @Valid
    private RemotePoint remotePoint;

}
