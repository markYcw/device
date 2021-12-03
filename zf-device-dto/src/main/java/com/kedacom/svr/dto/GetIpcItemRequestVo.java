package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 10:27
 * @description
 */
@Data
public class GetIpcItemRequestVo extends SvrRequestDto{

    @NotNull(message = "通道ID不能为空")
    @ApiModelProperty("通道ID")
    private Integer chnId;

}
