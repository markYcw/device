package com.kedacom.ums.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 9:29
 * @description TODO
 */
@Data
@ApiModel(value = "分组状态kafka消息")
public class DeviceGroupStatusModel implements Serializable {

    @ApiModelProperty(value = "分组ID")
    private String id;

    @ApiModelProperty(value = "父分组ID")
    private String parentId;

    @ApiModelProperty(value = "分组名")
    private String name;

    @ApiModelProperty(value = "分组类型：0-新媒体分组，1-自定义分组")
    private String type;

    @ApiModelProperty(value = "分组目录")
    private String groupCatalog;

    @ApiModelProperty(value = "分组码")
    private Integer groupCode;

    @ApiModelProperty(value = "排序索引")
    private Integer sortIndex;

    @ApiModelProperty(value = "操作类型 10:分组增加修改，11:分组删除")
    private Integer operateType;

}
