package com.kedacom.svr.dto;

import com.kedacom.svr.pojo.ChnInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 15:37
 * @description 添加/删除编码通道
 */
@Data
public class EnChnDto extends SvrRequestDto {

    @ApiModelProperty("0:添加 1：删除")
    private Integer type;

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("通道信息，添加时必填")
    private ChnInfo chnInfo;
}
