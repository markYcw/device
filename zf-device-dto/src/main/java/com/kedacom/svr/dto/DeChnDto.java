package com.kedacom.svr.dto;

import com.kedacom.svr.pojo.DeChnInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 16:06
 * @description 添加/删除解码通道
 */
@Data
public class DeChnDto extends SvrRequestDto{

    @ApiModelProperty("0:添加 1：删除")
    private Integer type;

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("通道信息，添加时必填")
    private DeChnInfo chnInfo;

}
