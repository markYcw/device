package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Auther: hxj
 * @Date: 2021/7/13 09:59
 */
@Data
@ApiModel(description =  "通知同步设备列表")
public class UmsDeviceGroupSyncRequestDto {

    @NotNull(message = "统一平台Id")
    @ApiModelProperty(value = "统一平台Id")
    private String umsId;

}
