package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/12
 */
@Data
public class PartitionVo implements Serializable {

    @ApiModelProperty("分区id")
    private Integer partId;

    @ApiModelProperty("分区名称")
    private String partName;

    @ApiModelProperty("分区总大小 MB")
    private Integer totalSize;

    @ApiModelProperty("分区空闲大小 MB")
    private Integer freeSize;

    @ApiModelProperty("分区状态：1：离线；2：未格式化；3：已格式化；4：未知")
    private Integer partState;

}
