package com.kedacom.mp.mcu.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @date: 2021/10/19 10:25
 * @description 添加/删除部门响应
 */
@Data
@ApiModel(description =  "添加/删除部门响应")
public class DepartmentVO implements Serializable {

    @ApiModelProperty("部门moid")
    private String departmentMoId;



}
