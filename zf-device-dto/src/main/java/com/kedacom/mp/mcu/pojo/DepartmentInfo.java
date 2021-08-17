package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/8/12 16:51
 * @Description 部门信息
 */
@Data
@ApiModel(value = "部门信息")
public class DepartmentInfo implements Serializable {

    @ApiModelProperty(value = "部门序号（UUID格式）")
    private String moid;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "职位信息")
    private String position;

}
