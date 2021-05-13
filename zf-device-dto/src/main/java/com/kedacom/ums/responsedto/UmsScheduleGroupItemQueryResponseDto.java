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
@ApiModel(value = "查询统一平台分组集合信息请求参数类")
public class UmsScheduleGroupItemQueryResponseDto implements Serializable {

    @ApiModelProperty(value = "分组Id")
    private String groupId;

    @ApiModelProperty(value = "平台Id")
    private String umsId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "分组排序字段")
    private Integer sortIndex;

    @ApiModelProperty(value = "当前分组设备总数")
    private Integer deviceTotalNum = 0;

    @ApiModelProperty(value = "当前分组在线设备总数")
    private Integer deviceOnlineNum = 0;

    @ApiModelProperty(value = "当前分组离线设备总数")
    private Integer deviceOfflineNum = 0;

    @ApiModelProperty(value = "当前分组故障设备总数")
    private Integer deviceFaultNum = 0;

}
