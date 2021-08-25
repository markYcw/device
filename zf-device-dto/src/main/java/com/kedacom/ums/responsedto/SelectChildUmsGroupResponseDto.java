package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Data
@ApiModel(description =  "根据当前分组ID查询子分组集合响应参数类")
public class SelectChildUmsGroupResponseDto implements Serializable {

    @ApiModelProperty(value = "分组Id")
    private String groupId;

    @ApiModelProperty(value = "平台Id")
    private String umsId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "分组排序字段")
    private Integer sortIndex;

}
