package com.kedacom.mp.mcu.response;

import com.kedacom.mp.mcu.pojo.Departments;
import com.kedacom.mp.mcu.pojo.TvWall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycw
 * @date: 2021/10/19 10:25
 * @description 查询所有部门响应
 */
@Data
@ApiModel(description =  "查询所有部门响应")
public class DepartmentsVO implements Serializable {

    @ApiModelProperty("分组类型 0-其他分组；1-特殊分组（号码组、来宾用户、未分组用户）；")
    private Integer seqencing;

    @ApiModelProperty("部门moid")
    private String departmentMoId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty(value = "部门列表")
    private List<Departments> departments;

}
