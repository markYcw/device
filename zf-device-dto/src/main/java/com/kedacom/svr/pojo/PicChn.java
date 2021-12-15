package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/15 17:00
 * @description 通道信息
 */
@Data
public class PicChn {

    @NotNull(message = "chnId不能为空")
    @ApiModelProperty(value = "通道ID （64位空通道)",required = true)
    private Integer chnId;

    @NotNull(message = "是否辅流不能为空")
    @ApiModelProperty(value = "是否辅流 0：主流 1：辅流",required = true)
    private Integer isSec;

}
