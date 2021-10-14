package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/8/12 16:51
 * @Description 部门信息
 */
@Data
@ApiModel(description =  "部门信息")
public class DepartmentInfo implements Serializable {

    @NotBlank(message = "部门序号 不能为空")
    @ApiModelProperty(value = "部门序号（UUID格式）",required = true)
    private String departmentMoId;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "职位信息")
    private String departmentPosition;

}
