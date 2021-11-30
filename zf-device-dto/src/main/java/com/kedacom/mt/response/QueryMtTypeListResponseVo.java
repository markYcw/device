package com.kedacom.mt.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/29
 */
@Data
@ApiModel("查询终端设备类型信息集合返回参数类")
public class QueryMtTypeListResponseVo implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "终端设备名称")
    private String mtName;

    @ApiModelProperty(value = "终端设备类型")
    private Integer mtType;

    @ApiModelProperty(value = "终端设备版本，0:3.0; 1:5.0")
    private Integer mtVersion;

}
