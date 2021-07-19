package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 15:00
 */
@Data
@ApiModel(value = "停止拉取媒体流")
public class StopPullUrlDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "设备国标id不能为空")
    @ApiModelProperty(value = "设备国标id", required = true)
    private String deviceID;

    @NotNull(message = "资源id不能为空")
    @ApiModelProperty(value = "资源ID;庭审场景，请使用LCXFF节点",required = true)
    private Integer sessionID;

}
