package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/22 9:34
 */
@Data
@ApiModel(description = "容量存储查询应答")
public class QueryMeetRecCapVO implements Serializable {

    @ApiModelProperty(value = "int64格式，总空间大小，单位字节")
    private Long totalSize;

    @ApiModelProperty(value = "int64格式，剩余空间大小，单位字节")
    private Long freeSize;

}
