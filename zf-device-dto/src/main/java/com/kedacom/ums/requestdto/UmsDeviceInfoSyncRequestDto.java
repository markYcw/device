package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Data
@ApiModel(description =  "通知同步设备列表")
public class UmsDeviceInfoSyncRequestDto {

    @NotNull(message = "统一平台Id")
    @ApiModelProperty(value = "统一平台Id")
    private String umsId;

}
