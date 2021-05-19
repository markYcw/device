package com.kedacom.device.ums;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@Data
public class DeviceGroupVo implements Serializable {

    @ApiModelProperty(value = "分组Id")
    private String id;

    @ApiModelProperty(value = "分组父Id")
    private String parentId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "分组类型：0-新媒体分组，1-自定义分组")
    private Integer type;

    @ApiModelProperty(value = "分组目录")
    private String groupCatalog;

    @ApiModelProperty(value = "分组码")
    private String groupCode;

    @ApiModelProperty(value = "分组排序字段")
    private Integer sortIndex;

}
