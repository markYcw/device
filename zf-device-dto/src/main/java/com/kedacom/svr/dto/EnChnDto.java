package com.kedacom.svr.dto;

import com.kedacom.svr.pojo.ChnInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 15:37
 * @description 添加/删除编码通道
 */
@Data
public class EnChnDto extends SvrRequestDto {

    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "0:添加 1：删除",required = true)
    private Integer type;

    @NotNull(message = "通道ID不能为空")
    @ApiModelProperty(value = "通道ID",required = true)
    private Integer chnId;

    @Valid
    @ApiModelProperty("通道信息，添加时必填")
    private ChnInfo chnInfo;
}
