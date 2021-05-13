package com.kedacom.ums.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Data
@ApiModel(value = "删除统一平台下的子设备")
public class UmsSubDeviceInfoDeleteRequestDto implements Serializable {

    @ApiModelProperty(value = "设备id集合")
    private List<String> ids;

}
