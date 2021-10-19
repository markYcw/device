package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycw
 * @date: 2021/10/19 13:28
 * @description 电视墙
 */
@Data
@ApiModel(description =  "部门")
public class Departments implements Serializable {

    @ApiModelProperty(value = "分组类型 0-其他分组；1-特殊分组（号码组、来宾用户、未分组用户）；")
    private Integer seqencing;

    @ApiModelProperty(value = "部门moid")
    private String departmentMoId;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "部门列表")
    private List<Departments> departments;


}
