package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/18 15:03
 */
@Data
@ApiModel("查询所有画面合成信息入参")
public class QueryAllVideoMixDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "画面合成设备分组id不能为空")
    @ApiModelProperty("画面合成设备分组id")
    private String groupID;

}
