package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 温湿度叠加
 */
@Data
public class DevList {

    @ApiModelProperty(value = "远程点名称",required = true)
    private Integer name;

    @ApiModelProperty(value = "远程点url",required = true)
    private Integer url;

}
