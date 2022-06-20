package com.kedacom.newMedia.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/4/11 15:37
 * @description 查询设备请求体
 */
@Data
public class NMDeviceListDto implements Serializable {

    @ApiModelProperty(value = "根据id查询")
    private String id;

    @ApiModelProperty(value = "根据名字模糊查询")
    private String name;

    @ApiModelProperty(value = "根据国标id查询")
    private String gbId;

    @ApiModelProperty(value = "根据设备类型查询")
    private Integer deviceType;

    @ApiModelProperty(value = "根据ip地址查询")
    private String ipv4;

    @ApiModelProperty(value = "根据父id查询")
    private String parentId;

    @ApiModelProperty(value = "根据分组id查询")
    private String groupId;

    @ApiModelProperty(value = "查询起始索引")
    private Integer queryIndex;

    @ApiModelProperty(value = "查询总数")
    private Integer queryCount;

}
