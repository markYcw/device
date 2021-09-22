package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 16:51
 * @description 获取解码参数
 */
@Data
public class DecParamDto extends SvrRequestDto{

    @ApiModelProperty("通道ID")
    private Integer chnId;

}
