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
@ApiModel(description = "磁阵信息")
public class DiskInfoVo implements Serializable {

    @ApiModelProperty("磁盘满策略: 1:满停录像; 2:满覆盖所有录像; 3:满覆盖普通录像; 4:未知")
    private Integer coverPolicy;

    @ApiModelProperty("磁盘列表信息")
    private List<DiskVo> disks;

}
