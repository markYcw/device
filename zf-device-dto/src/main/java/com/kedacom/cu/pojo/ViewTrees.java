package com.kedacom.cu.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *获取多视图设备树响应体
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 10:18
 * @description
 */
@Data
public class ViewTrees implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("root的id")
    private String rootId;

    @ApiModelProperty("类型")
    private Integer type;

}
