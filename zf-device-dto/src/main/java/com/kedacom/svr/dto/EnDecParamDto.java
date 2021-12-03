package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 19:01
 * @description 5.14设置解码参数
 */
@Data
public class EnDecParamDto extends SvrRequestDto{

    @NotNull(message = "通道ID不能为空")
    @ApiModelProperty(value = "通道ID",required = true)
    private Integer chnId;

    @NotNull(message = "解码的源通道ID不能为空")
    @ApiModelProperty(value = "解码的源通道ID",required = true)
    private Integer srcChnId;

    @NotNull(message = "是否解码辅流不能为空")
    @ApiModelProperty(value = "是否解码辅流",required = true)
    private Integer isSecChn;


}
