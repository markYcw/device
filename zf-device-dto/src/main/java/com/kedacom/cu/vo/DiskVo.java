package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/12
 */
@Data
@ApiModel(description = "磁盘列表信息")
public class DiskVo implements Serializable {

    @ApiModelProperty("磁盘id")
    private Integer diskId;

    @ApiModelProperty("磁盘名称")
    private String diskName;

    @ApiModelProperty("磁盘大小 MB")
    private Integer diskSize;

    @ApiModelProperty("磁盘状态，1：在线；2离线；3：未知")
    private Integer diskState;

    @ApiModelProperty("分区数量")
    private Integer partitionNum;

    @ApiModelProperty("分区列表")
    private List<PartitionVo> partitions;

}
