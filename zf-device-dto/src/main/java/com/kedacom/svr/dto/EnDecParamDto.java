package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 19:01
 * @description 5.14设置解码参数
 */
@Data
public class EnDecParamDto extends SvrRequestDto{

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("解码的源通道ID")
    private Integer srcChnId;

    @ApiModelProperty("是否解码辅流")
    private Integer isSecChn;


}
