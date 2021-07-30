package com.kedacom.msp.request.virtual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 18:18
 */
@Data
@ApiModel("拼控器通道推送入参")
public class DeviceReportRequest implements Serializable {

    @NotBlank(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

    @NotBlank
    @ApiModelProperty("必填 设备IP地址 ")
    private String devip;

    @ApiModelProperty("行为类型，推送或删除 选填，0=推送到统一设备，1=从统一设备删除，默认推送")
    private Integer action;

}
