package com.kedacom.aiBox.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/18
 */
@Data
@ApiModel(description = "查询AI-Box设备信息集合请求参数类")
public class QueryListRequestDto implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "设备名称")
    private String abName;

    @ApiModelProperty(value = "IP")
    private String abIp;

}
