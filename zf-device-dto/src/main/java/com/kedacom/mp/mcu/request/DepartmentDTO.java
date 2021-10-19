package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author ycw
 * @date: 2021/8/17 11:21
 * @description 创建/删除部门
 */
@Data
@ApiModel(description =  "创建/删除部门")
@ToString(callSuper = true)
public class DepartmentDTO extends McuRequestDTO{

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0：添加，1：删除", required = true)
    private Integer type;

    @ApiModelProperty(value = "部门moid 删除时必填", required = true)
    private String departmentMoId;

    @ApiModelProperty(value = "父部门moid 添加时必填")
    private String parentDepartmentMoId;

    @ApiModelProperty(value = "部门名称 添加时必填")
    private String departmentName;

}
