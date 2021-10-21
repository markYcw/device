package com.kedacom.aiBox.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/18
 */
@Data
@ApiModel(description = "查询AI-Box设备信息集合返回参数类")
public class QueryListResponseDto implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "设备名称")
    private String abName;

    @ApiModelProperty(value = "IP")
    private String abIp;

    @ApiModelProperty(value = "端口")
    private Integer abPort;

    @ApiModelProperty(value = "设备名称拼音(拼音+首字母)")
    private String abPinyin;

    @ApiModelProperty(value = "描述")
    private String abDesc;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
